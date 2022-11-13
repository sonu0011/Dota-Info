package com.sonu.ui_herolist.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonu.core.domain.DataState
import com.sonu.core.domain.Queue
import com.sonu.core.domain.UIComponent
import com.sonu.core.util.Logger
import com.sonu.hero_domain.HeroAttribute
import com.sonu.hero_domain.HeroFilter
import com.sonu.hero_interactors.FilterHeros
import com.sonu.hero_interactors.GetHeros
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HeroListViewModel @Inject constructor(
    private val getHeros: GetHeros,
    @Named("heroListLogger") private val logger: Logger,
    private val filterHeros: FilterHeros,
) : ViewModel() {

    val heroListState: MutableState<HeroListState> = mutableStateOf(HeroListState())

    init {
        onTriggerEvent(HeroListEvents.GetHeros)
    }

    fun onTriggerEvent(event: HeroListEvents) {
        when (event) {
            HeroListEvents.GetHeros -> {
                getHeros()
            }
            HeroListEvents.FilterHeros -> {
                filterHeros()
            }
            is HeroListEvents.UpdateHeroName -> {
                updateHeroName(event.heroName)
            }
            is HeroListEvents.UpdateHeroFilter -> {
                updateHeroFilter(event.heroFilter)
            }
            is HeroListEvents.UpdateFilterDialogState -> {
                heroListState.value =
                    heroListState.value.copy(filterDialogState = event.uiComponentState)
            }
            is HeroListEvents.UpdatePrimaryAttribute -> {
                updatePrimaryAttribute(event.primaryAttribute)
            }
            HeroListEvents.OnRemoveHeadFromQueue -> {
                removeHeadMessage()
            }
        }
    }

    private fun updatePrimaryAttribute(primaryAttribute: HeroAttribute) {
        heroListState.value = heroListState.value.copy(primaryAttribute = primaryAttribute)
        filterHeros()
    }

    private fun updateHeroFilter(heroFilter: HeroFilter) {
        heroListState.value = heroListState.value.copy(heroFilter = heroFilter)
        filterHeros()
    }

    private fun filterHeros() {
        val state = heroListState.value
        val filteredHeros = filterHeros.execute(
            current = state.heros,
            heroName = state.heroName,
            heroFilter = state.heroFilter,
            attributeFilter = state.primaryAttribute

        )
        heroListState.value = state.copy(filteredHeros = filteredHeros)
    }

    private fun updateHeroName(heroName: String) {
        heroListState.value = heroListState.value.copy(heroName = heroName)
    }

    private fun getHeros() {
        getHeros.execute().onEach { dataState ->
            logger.log(dataState.toString())
            when (dataState) {
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> {
                            appendToMessageQueue(dataState.uiComponent)
                        }
                        is UIComponent.None -> {
                            logger.log((dataState.uiComponent as UIComponent.None).message)
                        }
                    }
                }
                is DataState.Data -> {
                    heroListState.value = heroListState.value.copy(
                        heros = dataState.data ?: listOf()
                    )
                    filterHeros()
                }
                is DataState.Loading -> {
                    heroListState.value = heroListState.value.copy(
                        progressBarState = dataState.progressBarState
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun appendToMessageQueue(uiComponent: UIComponent) {
        val queue = heroListState.value.errorQueue
        queue.add(uiComponent)
        //force recompose
        heroListState.value = heroListState.value.copy(errorQueue = Queue(mutableListOf()))

        heroListState.value = heroListState.value.copy(errorQueue = queue)
    }

    private fun removeHeadMessage() {
        try {
            val queue = heroListState.value.errorQueue
            queue.remove()
            //force recompose
            heroListState.value = heroListState.value.copy(errorQueue = Queue(mutableListOf()))

            heroListState.value = heroListState.value.copy(errorQueue = queue)
        } catch (e: Exception) {
            logger.log("Nothing to remove from DialogQueue")
        }
    }

}
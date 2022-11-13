package com.sonu.ui_herodetail.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonu.core.domain.DataState
import com.sonu.core.domain.Queue
import com.sonu.core.domain.UIComponent
import com.sonu.core.util.Logger
import com.sonu.hero_interactors.GetHeroFromCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HeroDetailViewModel @Inject constructor(
    private val getHeroFromCache: GetHeroFromCache,
    @Named("heroDetail") private val logger: Logger,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val state: MutableState<HeroDetailState> = mutableStateOf(HeroDetailState())

    init {
        savedStateHandle.get<Int>("heroId")?.let { heroId ->
            onTriggerEvent(HeroDetailEvents.GetHeroFromCache(heroId))
        }
    }

    fun onTriggerEvent(event: HeroDetailEvents) {
        when (event) {
            is HeroDetailEvents.GetHeroFromCache -> {
                getHeroFromCache(event.id)
            }
            HeroDetailEvents.OnRemoveHeadFromQueue -> {
                removeHeadMessage()

            }
        }
    }

    private fun getHeroFromCache(id: Int) {
        getHeroFromCache.execute(id).onEach { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
                is DataState.Data -> {
                    state.value = state.value.copy(hero = dataState.data)
                }
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
            }
        }.launchIn(viewModelScope)
    }

    private fun appendToMessageQueue(uiComponent: UIComponent) {
        val queue = state.value.errorQueue
        queue.add(uiComponent)
        state.value = state.value.copy(errorQueue = Queue(mutableListOf())) //force recompose
        state.value = state.value.copy(errorQueue = queue)
    }
    private fun removeHeadMessage() {
        try {
            val queue = state.value.errorQueue
            queue.remove()
            //force recompose
            state.value = state.value.copy(errorQueue = Queue(mutableListOf()))

            state.value = state.value.copy(errorQueue = queue)
        } catch (e: Exception) {
            logger.log("Nothing to remove from DialogQueue")
        }
    }
    


}
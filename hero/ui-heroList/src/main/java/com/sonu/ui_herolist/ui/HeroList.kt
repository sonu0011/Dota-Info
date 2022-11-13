package com.sonu.ui_herolist.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.sonu.components.DefaultScreenUI
import com.sonu.core.domain.ProgressBarState
import com.sonu.core.domain.UIComponentState
import com.sonu.ui_herolist.components.HeroListFilter
import com.sonu.ui_herolist.components.HeroListItem
import com.sonu.ui_herolist.components.HeroListToolbar

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HeroList(
    imageLoader: ImageLoader,
    events: (HeroListEvents) -> Unit,
    state: HeroListState,
    onHeroItemClicked: (Int) -> Unit,
) {
    DefaultScreenUI(
        queue = state.errorQueue,
        onRemoveHeadFromQueue = {
            events(HeroListEvents.OnRemoveHeadFromQueue)
        },
        progressBarState = state.progressBarState
    ) {
        Column {
            HeroListToolbar(
                heroName = state.heroName,
                onHeroNameChanged = { name ->
                    events(HeroListEvents.UpdateHeroName(name))
                },
                onExecuteSearch = { events(HeroListEvents.FilterHeros) },
                onShowFilterDialog = {
                    events(HeroListEvents.UpdateFilterDialogState(UIComponentState.Show))
                }
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(state.filteredHeros) { hero ->
                    HeroListItem(
                        hero = hero,
                        onSelectHero = onHeroItemClicked,
                        imageLoader = imageLoader
                    )
                }
            }
        }
        if (state.filterDialogState is UIComponentState.Show) {
            HeroListFilter(
                heroFilter = state.heroFilter,
                onUpdateHeroFilter = { heroFilter ->
                    events(HeroListEvents.UpdateHeroFilter(heroFilter))
                },
                onCloseDialog = {
                    events(HeroListEvents.UpdateFilterDialogState(UIComponentState.Hide))
                },
                onUpdateAttributeFilter = { heroAttribute ->
                    events(HeroListEvents.UpdatePrimaryAttribute(heroAttribute))
                },
                attributeFilter = state.primaryAttribute
            )
        }
    }
}

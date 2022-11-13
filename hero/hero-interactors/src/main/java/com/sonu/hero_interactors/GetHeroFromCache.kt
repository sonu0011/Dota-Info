package com.sonu.hero_interactors

import com.sonu.core.domain.DataState
import com.sonu.core.domain.ProgressBarState
import com.sonu.core.domain.UIComponent
import com.sonu.hero_datasource.cache.HeroCache
import com.sonu.hero_domain.Hero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetHeroFromCache(
    private val cache: HeroCache
) {
    fun execute(
        id: Int
    ): Flow<DataState<Hero>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            //get data from cache
            val hero = cache.getHero(id)
                ?: throw  Exception("That hero does not exist in the cache.")

            emit(DataState.Data(hero))

        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                DataState.Response(
                    uiComponent = UIComponent.Dialog(
                        title = "Error",
                        description = e.message ?: "Unknown error"
                    )
                )
            )
        } finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }
}
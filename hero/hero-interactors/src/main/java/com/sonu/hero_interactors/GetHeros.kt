package com.sonu.hero_interactors

import com.sonu.core.domain.DataState
import com.sonu.core.domain.ProgressBarState
import com.sonu.core.domain.UIComponent
import com.sonu.hero_datasource.cache.HeroCache
import com.sonu.hero_datasource.network.HeroService
import com.sonu.hero_domain.Hero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetHeros(
    private val heroCache: HeroCache,
    private val service: HeroService,
) {
    fun execute(): Flow<DataState<List<Hero>>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
            val heros: List<Hero> = try { // catch network exceptions
                service.getHeroStats()
            } catch (e: Exception) {
                e.printStackTrace() // log to crashlytics?
                emit(
                    DataState.Response(
                        uiComponent = UIComponent.Dialog(
                            title = "Network Data Error",
                            description = e.message ?: "Unknown error"
                        )
                    )
                )
                listOf()
            }

            //cache the network data
            heroCache.insert(heros)

            //emit data from cache
            val cacheHeros = heroCache.selectAll()
            emit(DataState.Data(cacheHeros))
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
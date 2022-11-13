package com.sonu.ui_herolist.di

import com.sonu.core.util.Logger
import com.sonu.hero_interactors.FilterHeros
import com.sonu.hero_interactors.GetHeros
import com.sonu.hero_interactors.HeroInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeroListModule {

    @Provides
    @Singleton
    fun provideGetHeros(
        heroInteractors: HeroInteractors
    ): GetHeros {
        return heroInteractors.getHeros
    }

    @Provides
    @Singleton
    fun provideFilterHeros(
        heroInteractors: HeroInteractors
    ): FilterHeros {
        return heroInteractors.filterHeros
    }


    @Provides
    @Singleton
    @Named("heroListLogger")
    fun provideLogger(): Logger {
        return Logger(
            tag = "HeroList",
            isDebug = true
        )
    }
}
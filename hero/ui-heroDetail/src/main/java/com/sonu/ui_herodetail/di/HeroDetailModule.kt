package com.sonu.ui_herodetail.di

import com.sonu.core.util.Logger
import com.sonu.hero_interactors.GetHeroFromCache
import com.sonu.hero_interactors.HeroInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeroDetailModule {

    @Singleton
    @Provides
    fun provideGetHeroFromCache(
        interactors: HeroInteractors
    ): GetHeroFromCache {
        return interactors.getHeroFromCache
    }

    @Singleton
    @Provides
    @Named("heroDetail")
    fun provideLogger(): Logger {
        return Logger(
            tag = "heroDetail",
            isDebug = true
        )
    }
}
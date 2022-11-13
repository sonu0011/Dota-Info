package com.sonu.dotainfo.di

import android.app.Application
import coil.ImageLoader
import com.sonu.dotainfo.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesImageLoader(app: Application): ImageLoader {
        return ImageLoader.Builder(app)
            .availableMemoryPercentage(.25)
            .crossfade(true)
            .error(R.drawable.error_image)
            .placeholder(R.drawable.white_background)
            .build()
    }
}
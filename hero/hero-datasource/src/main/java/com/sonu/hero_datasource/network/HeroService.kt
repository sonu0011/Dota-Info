package com.sonu.hero_datasource.network

import com.sonu.hero_domain.Hero
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*

interface HeroService {
    suspend fun getHeroStats(): List<Hero>

    companion object Factory {
        fun build(): HeroService {
            return HeroServiceImpl(
                httpClient = HttpClient(Android) {
                    install(JsonFeature) {
                        serializer = KotlinxSerializer(
                             kotlinx.serialization.json.Json {
                                ignoreUnknownKeys =
                                    true // if the server returns extra field ignore them
                            }
                        )
                    }
                }
            )
        }
    }
}
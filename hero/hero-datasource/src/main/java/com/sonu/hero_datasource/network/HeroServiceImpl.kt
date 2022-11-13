package com.sonu.hero_datasource.network

import com.sonu.hero_datasource.network.model.HeroDto
import com.sonu.hero_datasource.network.model.toHero
import com.sonu.hero_domain.Hero
import io.ktor.client.*
import io.ktor.client.request.*

class HeroServiceImpl(
    private val httpClient: HttpClient
) : HeroService {

    override suspend fun getHeroStats(): List<Hero> {
        return httpClient.get<List<HeroDto>> {
            url(EndPoints.HERO_STATS)
        }.map { it.toHero() }
    }
}
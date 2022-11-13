package com.sonu.hero_datasource_test.network

import com.sonu.hero_datasource.network.model.HeroDto
import com.sonu.hero_datasource.network.model.toHero
import com.sonu.hero_domain.Hero
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private val json = Json {
    ignoreUnknownKeys = true
}

fun parseJsonData(jsonData: String): List<Hero> {
    val heros: List<Hero> = json.decodeFromString<List<HeroDto>>(jsonData).map { it.toHero() }
    return heros
}
package com.sonu.hero_interactors

import com.sonu.hero_datasource.cache.HeroCache
import com.sonu.hero_datasource.cache.HeroDatabase
import com.sonu.hero_datasource.network.HeroService
import com.squareup.sqldelight.db.SqlDriver


class HeroInteractors(
    val getHeros: GetHeros,
    val getHeroFromCache: GetHeroFromCache,
    val filterHeros: FilterHeros,
) {
    companion object Factory {
        fun build(sqlDriver: SqlDriver): HeroInteractors {
            val service = HeroService.build()
            val heroCache = HeroCache.Factory.build(sqlDriver)
            return HeroInteractors(
                getHeros = GetHeros(
                    service = service,
                    heroCache = heroCache
                ),
                getHeroFromCache = GetHeroFromCache(
                    cache = heroCache
                ),
                filterHeros = FilterHeros(),
            )
        }

        val schema: SqlDriver.Schema = HeroDatabase.Schema
        val dbName: String = "heros.db"
    }


}
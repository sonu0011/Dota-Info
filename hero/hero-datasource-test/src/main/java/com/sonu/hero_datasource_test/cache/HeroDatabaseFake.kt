package com.sonu.hero_datasource_test.cache

import com.sonu.hero_domain.Hero


class HeroDatabaseFake {

    val heros: MutableList<Hero> = mutableListOf()
}
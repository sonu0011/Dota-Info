package com.sonu.hero_interactors

import com.sonu.core.domain.FilterOrder
import com.sonu.hero_domain.Hero
import com.sonu.hero_domain.HeroAttribute
import com.sonu.hero_domain.HeroFilter
import kotlin.math.round

class FilterHeros {

    fun execute(
        current: List<Hero>,
        heroName: String,
        heroFilter: HeroFilter,
        attributeFilter: HeroAttribute
    ): List<Hero> {
        var filteredHeros: MutableList<Hero> =
            current.filter { it.localizedName.lowercase().contains(heroName.lowercase()) }
                .toMutableList()

        when (heroFilter) {
            is HeroFilter.Hero -> {
                when (heroFilter.order) {
                    FilterOrder.Ascending -> filteredHeros.sortBy { it.localizedName }
                    FilterOrder.Descending -> filteredHeros.sortByDescending { it.localizedName }
                }
            }
            is HeroFilter.ProWins -> {
                when (heroFilter.order) {
                    FilterOrder.Ascending -> {
                        filteredHeros.sortBy {
                            getWinRate(
                                it.proPick.toDouble(),
                                it.proWins.toDouble()
                            )
                        }
                    }
                    FilterOrder.Descending -> {
                        filteredHeros.sortByDescending {
                            getWinRate(
                                it.proPick.toDouble(),
                                it.proWins.toDouble()
                            )
                        }
                    }
                }

            }
        }

        when (attributeFilter) {
            HeroAttribute.Agility -> {
                filteredHeros =
                    filteredHeros.filter { it.primaryAttribute is HeroAttribute.Agility }
                        .toMutableList()
            }
            HeroAttribute.Intelligence -> {
                filteredHeros =
                    filteredHeros.filter { it.primaryAttribute is HeroAttribute.Intelligence }
                        .toMutableList()
            }
            HeroAttribute.Strength -> {
                filteredHeros =
                    filteredHeros.filter { it.primaryAttribute is HeroAttribute.Strength }
                        .toMutableList()
            }
            HeroAttribute.Unknown -> {
                // do not filter
            }
        }

        return filteredHeros
    }
}

private fun getWinRate(proPick: Double, proWins: Double): Int {
    return if (proPick <= 0) 0
    else round(proWins / proPick * 100).toInt()
}
package com.sonu.ui_herodetail.ui

sealed class HeroDetailEvents {

    data class GetHeroFromCache(
        val id: Int,
    ) : HeroDetailEvents()

    object OnRemoveHeadFromQueue : HeroDetailEvents()


}
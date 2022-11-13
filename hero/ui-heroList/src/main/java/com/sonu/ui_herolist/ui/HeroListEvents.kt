package com.sonu.ui_herolist.ui

import com.sonu.core.domain.UIComponentState
import com.sonu.hero_domain.HeroAttribute
import com.sonu.hero_domain.HeroFilter

sealed class HeroListEvents {
    object GetHeros : HeroListEvents()

    object FilterHeros : HeroListEvents()

    data class UpdateHeroName(
        val heroName: String
    ) : HeroListEvents()

    data class UpdateHeroFilter(
        val heroFilter: HeroFilter
    ) : HeroListEvents()

    data class UpdateFilterDialogState(
        val uiComponentState: UIComponentState
    ) : HeroListEvents()

    data class UpdatePrimaryAttribute(
        val primaryAttribute: HeroAttribute
    ) : HeroListEvents()

    object OnRemoveHeadFromQueue : HeroListEvents()
}

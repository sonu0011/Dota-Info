package com.sonu.ui_herolist.ui

import com.sonu.core.domain.ProgressBarState
import com.sonu.core.domain.Queue
import com.sonu.core.domain.UIComponent
import com.sonu.core.domain.UIComponentState
import com.sonu.hero_domain.Hero
import com.sonu.hero_domain.HeroAttribute
import com.sonu.hero_domain.HeroFilter

data class HeroListState(
    val heros: List<Hero> = listOf(),
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val heroName: String = "",
    val filteredHeros: List<Hero> = listOf(),
    val heroFilter: HeroFilter = HeroFilter.Hero(),
    val primaryAttribute: HeroAttribute = HeroAttribute.Unknown,
    val filterDialogState: UIComponentState = UIComponentState.Hide,
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf())

)

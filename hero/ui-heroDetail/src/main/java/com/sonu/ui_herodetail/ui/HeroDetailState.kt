package com.sonu.ui_herodetail.ui

import com.sonu.core.domain.ProgressBarState
import com.sonu.core.domain.Queue
import com.sonu.core.domain.UIComponent
import com.sonu.hero_domain.Hero

data class HeroDetailState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val hero: Hero? = null,
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf())
)
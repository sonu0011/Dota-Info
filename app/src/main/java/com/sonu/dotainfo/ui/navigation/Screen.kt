package com.sonu.dotainfo.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val arguments: List<NamedNavArgument>
) {

    object HeroList : Screen(
        route = "heroList",
        arguments = emptyList()
    )

    object HeroDetail : Screen(
        route = "heroDetail",
        arguments = listOf(
            navArgument(name = "heroId") {
                type = NavType.IntType
            }
        )
    )
}

package com.sonu.dotainfo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import com.sonu.dotainfo.ui.navigation.Screen
import com.sonu.dotainfo.ui.theme.DotaInfoTheme
import com.sonu.ui_herodetail.ui.HeroDetail
import com.sonu.ui_herodetail.ui.HeroDetailViewModel
import com.sonu.ui_herolist.ui.HeroList
import com.sonu.ui_herolist.ui.HeroListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DotaInfoTheme {
                BoxWithConstraints {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.HeroList.route,
                        builder = {
                            addHeroListScreen(
                                navController = navController,
                                imageLoader = imageLoader,
                                width = constraints.maxWidth / 2
                            )
                            addHeroDetailScreen(
                                imageLoader,
                                width = constraints.maxWidth / 2
                            )
                        }
                    )
                }
            }
        }
    }
}

fun NavGraphBuilder.addHeroListScreen(
    navController: NavController,
    imageLoader: ImageLoader,
    width: Int,
) {
    composable(
        route = Screen.HeroList.route,
    ) {
        val viewModel: HeroListViewModel = hiltViewModel()
        val state = viewModel.heroListState.value
        HeroList(
            state = state,
            events = viewModel::onTriggerEvent,
            imageLoader = imageLoader,
            onHeroItemClicked = { heroId ->
                navController.navigate(Screen.HeroDetail.route + "/$heroId")
            }
        )
    }
}

fun NavGraphBuilder.addHeroDetailScreen(
    imageLoader: ImageLoader,
    width: Int,
) {
    composable(
        route = Screen.HeroDetail.route + "/{heroId}",
        arguments = Screen.HeroDetail.arguments,

        ) { _ ->
        val heroDetailViewModel: HeroDetailViewModel = hiltViewModel()
        HeroDetail(
            state = heroDetailViewModel.state.value,
            imageLoader = imageLoader,
            events = heroDetailViewModel::onTriggerEvent
        )

    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DotaInfoTheme {
        Greeting("Android")
    }
}
/*
 * ContentNavHost.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pure.Serving

typealias LaunchBlock = (String) -> Unit

sealed class Navi(val route: String) {
    data object Home: Navi("home")
    data object Detail: Navi("detail")
}


@Composable
fun ContentNavHost(service: Serving,
                   navController: NavHostController,
                   onTitleChange: (String) -> Unit, modifier: Modifier = Modifier
) {
    NavHost(navController = navController,
        startDestination = Navi.Home.route,
        modifier = modifier) {
        composable(Navi.Home.route) {
            onTitleChange("Home")
            HomeView(service, launcher = { id ->
                navController.navigate(Navi.Detail.route + "/$id")
            } )
        }
        composable(Navi.Detail.route + "/{id}") {
            onTitleChange("Detail")
            it.arguments?.getString("id")?.let {
                DetailView(service, target = it)
            }
        }
    }
}
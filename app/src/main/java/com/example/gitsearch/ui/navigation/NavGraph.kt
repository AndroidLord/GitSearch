package com.example.gitsearch.ui.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gitsearch.ui.screen.home.HomeScreen
import com.example.gitsearch.ui.screen.homedetail.HomeDetailScreen

@Composable
fun MainNavigationGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = ScreenRoute.Home
    ){
        composable<ScreenRoute.Home> {
            HomeScreen(navController = navController)
        }
        composable<ScreenRoute.Detail> {
            val repoId = it.arguments?.getLong("repoId")
            HomeDetailScreen(repoId = repoId!!)
        }
    }

}
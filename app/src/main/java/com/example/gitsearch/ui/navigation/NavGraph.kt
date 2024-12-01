package com.example.gitsearch.ui.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.gitsearch.ui.screen.home.HomeScreen
import com.example.gitsearch.ui.screen.homedetail.HomeDetailScreen
import com.example.gitsearch.ui.screen.webview.WebViewScreen

@Composable
fun MainNavigationGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = ScreenRoute.Home
    ) {
        composable<ScreenRoute.Home> {
            HomeScreen(navController = navController)
        }
        composable<ScreenRoute.Detail> {
            val arg = it.toRoute<ScreenRoute.Detail>()
            HomeDetailScreen(navController = navController)
        }
        composable<ScreenRoute.WebView> {
            val arg = it.toRoute<ScreenRoute.WebView>()
            WebViewScreen(url = arg.url, onBackPressed = { navController.popBackStack() })
        }

    }

}
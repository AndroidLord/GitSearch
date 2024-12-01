package com.example.gitsearch.ui.navigation

import kotlinx.serialization.Serializable

sealed class ScreenRoute {

    @Serializable
    data object Home : ScreenRoute()

    @Serializable
    data class Detail(val repoId: Long) : ScreenRoute()
}
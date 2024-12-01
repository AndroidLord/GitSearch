package com.example.gitsearch.ui.screen.homedetail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun HomeDetailScreen(modifier: Modifier = Modifier, repoId: Long) {


    Text(
        text = "HomeDetailScreen $repoId",
        modifier = modifier
    )

}
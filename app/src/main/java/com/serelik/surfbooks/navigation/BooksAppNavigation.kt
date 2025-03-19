package com.serelik.moviedbcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.serelik.moviedbcompose.navigation.destination.toDetailsList
import com.serelik.moviedbcompose.navigation.destination.toFavoriteList
import com.serelik.moviedbcompose.navigation.destination.toSearchList

@Composable
fun BooksAppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "Search") {
        this.toSearchList(
            // onBookClick = { navController.navigate(route = "details") }
        )

        this.toDetailsList()

        this.toFavoriteList()
    }
}
package com.serelik.moviedbcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.serelik.moviedbcompose.navigation.destination.toDetailsList
import com.serelik.moviedbcompose.navigation.destination.toFavoriteList
import com.serelik.moviedbcompose.navigation.destination.toSearchList
import com.serelik.surfbooks.navigation.BottomNavigationScreens

@Composable
fun BooksAppNavigation(
    navController: NavHostController,
    onFavoriteClickSnackBar: (isFavorite: Boolean) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationScreens.SEARCH_ROUTE
    ) {
        this.toSearchList(
            navController,
            onFavoriteClickSnackBar
        )

        this.toDetailsList(onBackClick = navController::popBackStack, onFavoriteClickSnackBar)

        this.toFavoriteList(navController, onFavoriteClickSnackBar)
    }
}
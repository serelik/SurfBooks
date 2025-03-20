package com.serelik.moviedbcompose.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.serelik.surfbooks.ui.details.BooksDetailsScreen
import com.serelik.surfbooks.ui.search.BooksListScreen
import com.serelik.surfbooks.ui.FavoriteBooksListScreen

const val ID_KEY = "id"

fun NavGraphBuilder.toSearchList(navHostController: NavHostController) {
    composable("Поиск") {
        BooksListScreen(
            navController = navHostController
        )
    }
}

fun NavGraphBuilder.toFavoriteList() {
    composable("Избранное") {
        FavoriteBooksListScreen()
    }
}

fun NavGraphBuilder.toDetailsList() {


    composable(
        "Details/{$ID_KEY}", arguments = listOf(
        navArgument(ID_KEY) {
            type = NavType.StringType
            nullable = false
        }
    )) {

        BooksDetailsScreen()
    }
}
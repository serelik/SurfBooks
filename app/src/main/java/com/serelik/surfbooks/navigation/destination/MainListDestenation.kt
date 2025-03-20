package com.serelik.moviedbcompose.navigation.destination

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.serelik.surfbooks.ui.details.BooksDetailsScreen
import com.serelik.surfbooks.ui.search.BooksListScreen
import com.serelik.surfbooks.ui.favorite.FavoriteBooksListScreen

const val ID_KEY = "id"

fun NavGraphBuilder.toSearchList(navHostController: NavHostController) {
    composable("Поиск") {
        BooksListScreen(
            onItemClick = navHostController::navigateToBookDetails
        )
    }
}

fun NavGraphBuilder.toFavoriteList(navHostController: NavHostController) {
    composable("Избранное") {
        FavoriteBooksListScreen(
            onBackClick = navHostController::popBackStack,
            onItemClick = navHostController::navigateToBookDetails
        )
    }
}

fun NavGraphBuilder.toDetailsList(onBackClick: () -> Unit) {


    composable(
        "Details/{$ID_KEY}", arguments = listOf(
            navArgument(ID_KEY) {
                type = NavType.StringType
                nullable = false
            }
        )) {

        BooksDetailsScreen(onBackClick)
    }
}

fun NavController.navigateToBookDetails(bookId: String) {
    navigate("Details/${bookId}")
}
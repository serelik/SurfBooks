package com.serelik.moviedbcompose.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.serelik.surfbooks.ui.BooksDetailsScreen
import com.serelik.surfbooks.ui.BooksListScreen
import com.serelik.surfbooks.ui.FavoriteBooksListScreen


fun NavGraphBuilder.toSearchList() {
    composable("Search") {
        BooksListScreen()
    }
}

fun NavGraphBuilder.toFavoriteList() {
    composable("Favorite") {
        FavoriteBooksListScreen()
    }
}

fun NavGraphBuilder.toDetailsList() {
    composable("Details") {
        BooksDetailsScreen()
    }
}
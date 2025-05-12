package com.serelik.surfbooks.navigation

import com.serelik.surfbooks.R

sealed class BottomNavigationScreens(
    val route: String,
    val icon: Int
) {
    data object Search :
        BottomNavigationScreens(
            SEARCH_ROUTE,
            R.drawable.search_icon
        )

    data object Favorite :
        BottomNavigationScreens(
            FAVORITE_ROUTE,
            R.drawable.favorite_icon
        )

    companion object {
        const val SEARCH_ROUTE = "Поиск"
        const val FAVORITE_ROUTE = "Избранное"
    }
}
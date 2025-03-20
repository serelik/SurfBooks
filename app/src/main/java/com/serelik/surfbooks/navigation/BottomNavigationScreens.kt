package com.serelik.surfbooks.navigation

import com.serelik.surfbooks.R

sealed class BottomNavigationScreens(
    val route: String,
    val icon: Int
) {
    data object Search :
        BottomNavigationScreens(
            "Поиск",
            R.drawable.search_icon
        )

    data object Favorite :
        BottomNavigationScreens(
            "Избранное",
            R.drawable.favorite_icon
        )
}
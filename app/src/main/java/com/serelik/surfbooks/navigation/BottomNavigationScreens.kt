package com.serelik.surfbooks.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.serelik.surfbooks.R

sealed class BottomNavigationScreens(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector
) {
    data object Search :
        BottomNavigationScreens("Search", R.string.search_screen_route, Icons.Default.Search)

    data object Favorite :
        BottomNavigationScreens("Favorite", R.string.favorite_screen_route, Icons.Filled.Favorite)
}
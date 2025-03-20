package com.serelik.surfbooks.navigation

import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.serelik.surfbooks.R

sealed class BottomNavigationScreens(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: Int
) {
    data object Search :
        BottomNavigationScreens(
            "Поиск",
            R.string.search_screen_route, R.drawable.search_icon
        )

    data object Favorite :
        BottomNavigationScreens(
            "Избранное",
            R.string.favorite_screen_route,
            R.drawable.favorite_icon
        )
}
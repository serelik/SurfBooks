package com.serelik.surfbooks.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.serelik.moviedbcompose.navigation.BooksAppNavigation
import com.serelik.surfbooks.navigation.BottomNavigationScreens

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Search,
        BottomNavigationScreens.Favorite,
    )

    Scaffold(
        contentWindowInsets = WindowInsets.ime,
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BooksAppBottomNavigation(navController, bottomNavigationItems)
        },
    ) { padding ->
        Modifier.padding(padding)
        BooksAppNavigation(navController)
    }
}


@Composable
private fun BooksAppBottomNavigation(
    navController: NavHostController,
    items: List<BottomNavigationScreens>
) {
    NavigationBar {
        items.forEach { item ->
            AddItem(
                screen = item,
                navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomNavigationScreens,
    navController: NavHostController

) {
    NavigationBarItem(
        label = {
            Text(text = screen.route)
        },

        icon = {
            Icon(screen.icon, screen.route)
        },

        selected = true,
        alwaysShowLabel = true,
        onClick = { navController.navigate(screen.route) },
        colors = NavigationBarItemColors(
            selectedTextColor = Color.Cyan,
            selectedIconColor = Color.Cyan,
            selectedIndicatorColor = Color.Transparent,
            unselectedIconColor = Color.Gray,
            unselectedTextColor = Color.Gray,
            disabledIconColor = Color.Black,
            disabledTextColor = Color.Black,
        )
    )
}

@Preview
@Composable
fun MainScreePreview() {
    MainScreen()
}
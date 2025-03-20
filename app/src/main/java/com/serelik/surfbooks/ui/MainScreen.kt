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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.serelik.moviedbcompose.navigation.BooksAppNavigation
import com.serelik.surfbooks.navigation.BottomNavigationScreens
import com.serelik.surfbooks.ui.theme.LightBlue

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Search,
        BottomNavigationScreens.Favorite,
    )

    Scaffold(
        contentWindowInsets = WindowInsets.ime,
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (navBackStackEntry?.destination?.route?.startsWith("Details") == false) {
                BooksAppBottomNavigation(navController, bottomNavigationItems)
            }
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
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBarItem(
        label = {
            Text(text = screen.route)
        },
        icon = {
            val imageVector = ImageVector.vectorResource(screen.icon)
            Icon(imageVector, screen.route)
        },
        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
        alwaysShowLabel = true,
        onClick = { navController.navigate(screen.route) },
        colors = NavigationBarItemColors(
            selectedTextColor = LightBlue,
            selectedIconColor = LightBlue,
            selectedIndicatorColor = Color.Transparent,
            unselectedIconColor = Color.LightGray,
            unselectedTextColor = Color.LightGray,
            disabledIconColor = Color.Black,
            disabledTextColor = Color.Black,
        )
    )
}
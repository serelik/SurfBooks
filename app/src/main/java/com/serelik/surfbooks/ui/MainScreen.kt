package com.serelik.surfbooks.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.serelik.moviedbcompose.navigation.BooksAppNavigation
import com.serelik.surfbooks.navigation.BottomNavigationScreens
import com.serelik.surfbooks.ui.common.BookSnackBarVisuals
import com.serelik.surfbooks.ui.theme.LightBlue
import com.serelik.surfbooks.ui.theme.LightRed
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Search,
        BottomNavigationScreens.Favorite,
    )

    val scaffoldState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val showSnackBar: (Boolean) -> Unit = { isFavorite ->

        val snackBarVisualParams =
            if (isFavorite) {
                Pair("Книга успешно удалена из избранного", LightRed)
            } else Pair("Книга успешно добавлена в избранное", LightBlue)
        scope.launch {
            scaffoldState.showSnackbar(
                BookSnackBarVisuals(
                    message = snackBarVisualParams.first,
                    color = snackBarVisualParams.second,
                )
            )
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets.ime,
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (navBackStackEntry?.destination?.route?.startsWith("Details") == false) {
                BooksAppBottomNavigation(navController, bottomNavigationItems)
            }
        },
        snackbarHost = {
            SnackbarHost(scaffoldState) { data ->
                val color = (data.visuals as? BookSnackBarVisuals)?.color ?: Color.Red
                Snackbar(
                    snackbarData = data,
                    containerColor = color,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }
        }

    ) { padding ->
        Modifier.padding(padding)
        BooksAppNavigation(
            navController,
            showSnackBar
        )


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
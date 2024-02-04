package com.example.chatapp.presentation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.chatapp.R
import com.example.chatapp.constants.BlueTheme
import kotlinx.coroutines.selects.select

@Composable
fun BottomBar(navController: NavHostController){

    val screens = listOf(
        BottomBarScreen.Chat,
        BottomBarScreen.Search,
        BottomBarScreen.Status,
        BottomBarScreen.Profile,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        BottomAppBar {
            screens.forEach {bottomBarScreen ->
                AddItem(
                    screen = bottomBarScreen,
                    currentDestination =currentDestination ,
                    navController =navController
                )
            }

        }
    }

}



@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                painter = painterResource(id =screen.icon ),
                contentDescription = screen.title,
                tint = if(currentDestination?.hierarchy?.any {
                    it.route == screen.route }==true){
                    BlueTheme
                }else Color.DarkGray
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,

        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}




abstract class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int
) {
    object Chat : BottomBarScreen(
        route = "chat",
        title = "Chat",
        icon = R.drawable.outline_chat_24
    )

    object Search: BottomBarScreen(
        route = "search",
        title = "Search",
        icon = R.drawable.outline_search_24
    )
    object Status : BottomBarScreen(
        route = "status",
        title = "Status",
        icon = R.drawable.outline_slow_motion_video_24
    )

    object Profile : BottomBarScreen(
        route = "profile",
        title = "Profile",
        icon = R.drawable.outline_account_circle_24
    )
}
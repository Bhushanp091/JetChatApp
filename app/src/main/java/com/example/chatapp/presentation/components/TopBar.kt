package com.example.chatapp.presentation.components



import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.chatapp.constants.BlueTheme
import com.example.chatapp.constants.Darkcolor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarHome(scrollBehavior: TopAppBarScrollBehavior,navController: NavController) {

    val screens = listOf(
        BottomBarScreen.Chat,
        BottomBarScreen.Status,
        BottomBarScreen.Profile,
    )

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val topBar = screens.any{it.route == currentRoute}
    val title = screens.find { it.route == currentRoute }?.title ?: ""
    if (topBar){
        TopAppBar(
            title = { Column(
            ) {
                Text(text = title, color = Color.White, fontWeight = FontWeight.ExtraBold)
            } },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = BlueTheme),
            scrollBehavior = scrollBehavior,
            modifier = Modifier.shadow(elevation = 10.dp)
        )
    }


}


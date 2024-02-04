package com.example.chatapp.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.presentation.components.BottomBar
import com.example.chatapp.presentation.components.TopBarHome
import com.example.chatapp.presentation.viewmodel.ChatAppViewmodel
import com.example.chatapp.util.commonProgressBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeNavigation(
    viewmodel: ChatAppViewmodel,
    navHostController: NavHostController=rememberNavController(),
    onLogOut:()->Unit,
    onChat:(String)->Unit
){

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold (
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize(),
        topBar = { TopBarHome(scrollBehavior = scrollBehavior, navController = navHostController)},
        bottomBar = { BottomBar(navController = navHostController)}
    ){paddingValues ->
        HomeNavScreen(navHostController,paddingValues,viewmodel,{onLogOut()},{onChat(it)})

    }

}
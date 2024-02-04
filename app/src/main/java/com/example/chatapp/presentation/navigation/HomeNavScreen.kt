package com.example.chatapp.presentation.navigation





import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.presentation.components.BottomBarScreen
import com.example.chatapp.presentation.viewmodel.ChatAppViewmodel
import com.example.chatapp.screens.home.Home.SenderChatScreen
import com.example.chatapp.screens.home.Home.homeScreen
import com.example.chatapp.screens.home.ProfileScreen
import com.example.chatapp.screens.home.Search.searchScreen
import com.example.chatapp.screens.home.Home.homeScreenMain
import com.example.chatapp.screens.home.statusScreen


@Composable
fun HomeNavScreen(
    navHostController: NavHostController = rememberNavController(),
    paddingValues: PaddingValues,
    viewmodel: ChatAppViewmodel,
    onLogOut:()->Unit,
    onChat: (String) -> Unit
){

    NavHost(
        modifier = Modifier.padding(paddingValues),
        navController = navHostController,
        route = Graph.Home.route,
        startDestination = BottomBarScreen.Chat.route
    ){
        composable(BottomBarScreen.Chat.route){
            homeScreenMain(viewmodel){ onChat(it)}

        }
        composable(BottomBarScreen.Search.route){
            searchScreen(viewmodel)
        }
        composable(BottomBarScreen.Status.route){
            homeScreen(viewmodel) {
                onLogOut()
            }
        }
        composable(BottomBarScreen.Profile.route){
            ProfileScreen(viewmodel)
        }

    }



}


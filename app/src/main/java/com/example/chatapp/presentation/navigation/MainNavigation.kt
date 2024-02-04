package com.example.chatapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.chatapp.presentation.viewmodel.ChatAppViewmodel
import com.example.chatapp.screens.home.Home.SenderChatScreen

@Composable
fun MainNavigation(
    navController: NavHostController,
    viewmodel: ChatAppViewmodel,
    ) {

    NavHost(
        navController = navController,
        route = Graph.Root.route,
        startDestination = viewmodel.checkAlreadySignedIn()
    ){

        authscreen(navController = navController, viewmodel =viewmodel )
        composable(Graph.Home.route){
            HomeNavigation(
                viewmodel = viewmodel,
                onLogOut = { navigateTo(navController,Graph.Authentication.route) },
                onChat =  {
                   navController.navigate(Graph.Chat.route.replace("{userId}", it))
                })

            }
        composable(Graph.Chat.route){ backStackEntry ->
                val userId = backStackEntry.arguments?.getString("userId") ?: ""
                SenderChatScreen(userId,viewmodel)
        }
        }
    }


sealed class Graph(val route:String){
    object Root:Graph("root")
    object Authentication:Graph("authentication")
    object Home:Graph("homw")
    object Chat:Graph("chat/{userId}")
}




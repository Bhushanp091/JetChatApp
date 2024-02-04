package com.example.chatapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.chatapp.presentation.viewmodel.ChatAppViewmodel
import com.example.chatapp.screens.login.LoginScreen
import com.example.chatapp.screens.signIn.SignupScreen


fun NavGraphBuilder.authscreen(
    navController: NavController,
    viewmodel:ChatAppViewmodel
){
    navigation(
        route = Graph.Authentication.route,
        startDestination = AuthNavigation.LogInScreen.route
    ){
        composable(AuthNavigation.SignInScreen.route){
            SignupScreen(navController,viewmodel)
        }
        composable(AuthNavigation.LogInScreen.route){
            LoginScreen(navController,viewmodel)
        }
    }

}

sealed class AuthNavigation(val route:String){
    object SignInScreen:AuthNavigation("signIn")
    object LogInScreen:AuthNavigation("login")
    object ForegotScreen:AuthNavigation("forgot")
}
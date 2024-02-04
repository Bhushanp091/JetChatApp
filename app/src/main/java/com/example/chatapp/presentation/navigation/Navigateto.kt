package com.example.chatapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController


fun navigateTo(navController: NavController,destination:String){
    navController.navigate(destination){
        popUpTo(Graph.Home.route) {inclusive = true }
    }
}
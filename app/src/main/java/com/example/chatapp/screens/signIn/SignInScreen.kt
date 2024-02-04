package com.example.chatapp.screens.signIn

import androidx.compose.ui.platform.LocalContext


import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chatapp.presentation.navigation.AuthNavigation
import com.example.chatapp.presentation.navigation.Graph
import com.example.chatapp.presentation.navigation.navigateTo
import com.example.chatapp.presentation.viewmodel.ChatAppViewmodel
import com.example.chatapp.util.commonProgressBar


@Composable
fun SignupScreen(navController: NavController, viewmodel: ChatAppViewmodel) {

    val inputName = remember { mutableStateOf("")}
    val inputEmail = remember { mutableStateOf("")}
    val inputPassword = remember { mutableStateOf("")}

    Box (
        modifier = Modifier
            .fillMaxSize()
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally

        ){
            Text(text = "Sign UP",
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif)
            OutlinedTextField(
                value =inputName.value ,
                onValueChange = { inputName.value = it } ,
                label = { Text(text = "Name")},
                modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(
                value =inputEmail.value ,
                onValueChange = { inputEmail.value = it },
                label = { Text(text = "Email",)},
                modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(
                value =inputPassword.value ,
                onValueChange = { inputPassword.value = it } ,
                label = { Text(text ="Password" )},
                modifier = Modifier.padding(8.dp)
            )
            val context = LocalContext.current
            Button(

                onClick = {
                    if(inputName.value.isEmpty()&&
                        inputPassword.value.isEmpty()&&
                        inputEmail.value.isEmpty()
                    ){
                        Toast.makeText(
                            context, "Please fill All TextField", Toast.LENGTH_LONG
                        ).show()

                    }else{
                      viewmodel.onSignUp(inputEmail.value,inputPassword.value,inputName.value){ navigateTo(navController,Graph.Home.route) }
                    }

                    inputName.value = ""
                    inputEmail.value = ""
                    inputPassword.value = ""

                },
                modifier = Modifier.padding(8.dp),

                ){
                Text(text = "Register")
            }

            Text(
                text = "Already have account? Login Here",
                color = Color.Blue,
                modifier = Modifier.clickable {
                        navigateTo(navController,AuthNavigation.LogInScreen.route)
                })
        }

        if(viewmodel.inProgress.value ){
            commonProgressBar()
        }
    }

}
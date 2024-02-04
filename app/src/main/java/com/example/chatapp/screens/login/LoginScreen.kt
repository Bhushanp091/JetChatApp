package com.example.chatapp.screens.login


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
import androidx.compose.ui.platform.LocalContext
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
fun LoginScreen(
    navController: NavController,
    viewmodel: ChatAppViewmodel,
) {

    val inputEmail = remember { mutableStateOf("") }
    val inputPassword = remember { mutableStateOf("") }

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
            Text(text = "LogIn",
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif)


            OutlinedTextField(
                value =inputEmail.value ,
                onValueChange = { inputEmail.value = it } ,
                label = { Text(text = "Email")},
                modifier = Modifier.padding(8.dp)
            )

            OutlinedTextField(
                value =inputPassword.value ,
                onValueChange = { inputPassword.value = it },
                label = { Text(text = "Password",)},
                modifier = Modifier.padding(8.dp)
            )

            val context = LocalContext.current
            Button(

                onClick = {
                    if(
                        inputPassword.value.isEmpty()&&
                        inputEmail.value.isEmpty()
                    ){
                        Toast.makeText(
                            context, "Please fill All TextField", Toast.LENGTH_LONG
                        ).show()

                    }else{
                        viewmodel.logIn(inputEmail.value,inputPassword.value) {
                            viewmodel.inProgress.value = false
                            navigateTo(navController,Graph.Home.route)
                        }
                    }


                    inputEmail.value = ""
                    inputPassword.value = ""

                },
                modifier = Modifier.padding(8.dp),

                ){
                Text(text = "LogIn")
            }

            Text(
                text = "Don't have account? Register Here",
                color = Color.Blue,
                modifier = Modifier.clickable {
                        navigateTo(navController,AuthNavigation.SignInScreen.route )
                })
        }
        if(viewmodel.inProgress.value ){
            commonProgressBar()
        }
    }


}
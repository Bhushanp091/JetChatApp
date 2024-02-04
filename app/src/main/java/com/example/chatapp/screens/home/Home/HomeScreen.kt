package com.example.chatapp.screens.home.Home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.chatapp.presentation.viewmodel.ChatAppViewmodel


@Composable
fun homeScreenMain(
    viewModel:ChatAppViewmodel,
    onChat: (String) -> Unit
){
    val uid = viewModel.currentUserData.value
    Column (
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        if (viewModel.chats.value.isEmpty()){
            Text(text = "No Chats Avaliable")
        }else{
            LazyColumn(
            ){

                items(viewModel.chats.value){chat->
                    val chats = if(chat.user1.uid == uid?.uid){
                        chat.user2
                    }else{
                        chat.user1
                    }

                    ChatUser(chats){onChat(it)}

                }
            }
        }

    }
}

@Composable
fun homeScreen(
    viewmodel: ChatAppViewmodel,
    onLogout:()->Unit
    ) {
    Button(onClick = {
        viewmodel.inProgress.value = true
        onLogout()
        viewmodel.logOut()
        viewmodel.inProgress.value = false
    }) {
        Text(text = "Log Out")
    }

}
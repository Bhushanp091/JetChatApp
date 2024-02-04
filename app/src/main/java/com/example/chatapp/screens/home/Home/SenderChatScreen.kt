package com.example.chatapp.screens.home.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapp.R
import com.example.chatapp.constants.BlueTheme
import com.example.chatapp.constants.GrayColor
import com.example.chatapp.presentation.viewmodel.ChatAppViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SenderChatScreen(userId: String,viewmodel: ChatAppViewmodel) {
    LaunchedEffect(key1 = true){
        viewmodel.getChatUserData(userId)
    }


    val userInfo = viewmodel.chatUser
    var inputText = remember { mutableStateOf("") }


    Column (
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxSize()
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Row {
                Box(
                    modifier = Modifier
                        .size(35.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                }
                Box(
                    modifier = Modifier
                        .size(45.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Image(
                        painter = painterResource(
                            id =
                            if (userInfo.value?.imageUrl == null) {
                                R.drawable.empty_profile
                            } else {
                                R.drawable.arms_beginner
                            }
                        ),
                        contentDescription = "profileImage",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.padding(6.dp))
                Column {
                    userInfo.value?.name?.let { Text(text = it, fontWeight = FontWeight.SemiBold) }
                    userInfo.value?.bio?.let { Text(text = it, color = GrayColor, fontSize = 13.5.sp) }
                }
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "back")
            }



        }
        Divider()
        LazyColumn(
            modifier = Modifier
                .weight(1f)
        ) {
            items(100) { index ->
                Text("Item $index")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = inputText.value,
            onValueChange = {inputText.value = it},
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            placeholder = { Text(text = "Message")},
            trailingIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = null,
                    )
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = BlueTheme,
                focusedIndicatorColor = BlueTheme,
            ),
            shape = RoundedCornerShape(25.dp)
        )
    }

}
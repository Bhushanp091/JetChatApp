package com.example.chatapp.screens.home.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapp.R
import com.example.chatapp.constants.GrayColor
import com.example.chatapp.domain.model.ChatUser

@Composable
fun ChatUser(chat: ChatUser,onChat:(String)->Unit) {
    Spacer(modifier = Modifier.padding(6.dp))
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable{ chat.uid?.let { onChat(it) } },
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Row {
            Box(
                modifier = Modifier
                    .size(50.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Image(
                    painter = painterResource(
                        id =
                        if (chat.imageUrl == null) {
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
            Spacer(modifier = Modifier.padding(8.dp))
            Column {
                chat.name?.let { Text(text = it, fontWeight = FontWeight.SemiBold) }
                chat.bio?.let { Text(text = it, color = GrayColor, fontSize = 13.5.sp) }
            }
        }


    }
}
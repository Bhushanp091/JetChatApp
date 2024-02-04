package com.example.chatapp.screens.home.Search

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.chatapp.constants.BlueTheme
import com.example.chatapp.constants.GrayColor
import com.example.chatapp.domain.model.UserData

@Composable
fun SearchResult(user: UserData) {
    Spacer(modifier = Modifier.padding(8.dp))
    Row (
        modifier = Modifier
            .fillMaxWidth(),
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
                            if (user.imageUrl == null) {
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
                user.name?.let {
                    Text(text = user.name, fontWeight = FontWeight.SemiBold)
                    user.bio?.let { Text(text = it, color = GrayColor, fontSize = 13.5.sp) }
                }
            }
        }
        IconButton(onClick = {}) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "add", tint = BlueTheme)
        }
    }

}
package com.example.chatapp.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapp.R
import com.example.chatapp.constants.BlueTheme
import com.example.chatapp.constants.GrayColor
import com.example.chatapp.presentation.components.InfoCard
import com.example.chatapp.presentation.viewmodel.ChatAppViewmodel
import com.example.chatapp.util.commonProgressBar

@Composable
fun ProfileScreen(viewmodel: ChatAppViewmodel){

    val userInfo =viewmodel.currentUserData.value

   Column (
       modifier = Modifier
           .fillMaxSize()
           .padding(vertical = 40.dp),
       horizontalAlignment = Alignment.CenterHorizontally
   ){
       Box (
           modifier = Modifier
               .size(150.dp),
           contentAlignment = Alignment.BottomEnd
       ){
           Image(
               painter = painterResource(id =
               if (userInfo?.imageUrl==null){
                   R.drawable.empty_profile
               }else{R.drawable.arms_beginner}) ,
               contentDescription = "profileImage",
               contentScale = ContentScale.Crop,
               modifier = Modifier
                   .clip(CircleShape)
                   .fillMaxSize()
           )
           Box(
               modifier = Modifier
                   .size(45.dp)
                   .clip(CircleShape)
                   .background(BlueTheme)

           ){
               IconButton(onClick = { /*TODO*/ }) {
                   Icon(
                       painter = painterResource(id = R.drawable.baseline_camera_alt_24),
                       contentDescription ="camera",
                       modifier = Modifier
                           .clip(CircleShape)
                      ,
                       tint = Color.White

                   )
               }
           }
       }

    Spacer(modifier = Modifier.padding(15.dp))
       InfoCard(R.drawable.baseline_person_24, "Name",userInfo?.name, viewmodel)
       InfoCard(R.drawable.baseline_info_outline_24,"Bio", userInfo?.bio,viewmodel)
       InfoCard(R.drawable.baseline_email_24,"Email", userInfo?.email,viewmodel)
   }
    Spacer(modifier = Modifier.padding(8.dp))
    if (viewmodel.inProgress.value){
        commonProgressBar()
    }
}



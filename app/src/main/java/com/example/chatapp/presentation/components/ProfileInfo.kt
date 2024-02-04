package com.example.chatapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapp.constants.BlueTheme
import com.example.chatapp.constants.GrayColor
import com.example.chatapp.presentation.viewmodel.ChatAppViewmodel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoCard(
    icon:Int,
    title:String,
    content: String?,
    viewmodel: ChatAppViewmodel
){
    var showAlertBox = remember { mutableStateOf(false) }
    val inputText = remember { mutableStateOf("") }
    val userName = viewmodel.currentUserData.value?.name
    val userBio = viewmodel.currentUserData.value?.bio
    val userPhone = viewmodel.currentUserData.value?.email
    val userImage = viewmodel.currentUserData.value?.imageUrl

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Row {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(id = icon), contentDescription =title, tint = GrayColor )
            }
            Column {
                Text(text = title, color = GrayColor, fontSize = 13.5.sp)
                if (content != null) {
                    Text(text = content, fontWeight = FontWeight.SemiBold)
                }
            }
        }
        if (title!="Email"){
            IconButton(onClick = {showAlertBox.value=true}) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = "edit", tint = BlueTheme)
            }
        }


    }

    if(showAlertBox.value) {
        AlertDialog(
            modifier = Modifier
                .fillMaxWidth(),
            onDismissRequest = {showAlertBox.value=false},
            text = {
                Column (
                    modifier = Modifier
                ){
                    Text(text = "Change $title", fontSize =20.sp, color = Color.DarkGray)
                    Spacer(modifier = Modifier.padding(10.dp))
                    OutlinedTextField(
                        enabled = true,
                        modifier = Modifier.defaultMinSize(minHeight = 40.dp),
                        value = inputText.value,
                        onValueChange = { inputText.value = it },
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = BlueTheme,
                            focusedIndicatorColor = BlueTheme,
                            unfocusedIndicatorColor = Color.LightGray
                        ),
                        placeholder = { Text(text = title, color = GrayColor)},
                    )

                }
            },
            confirmButton = {

                TextButton(
                    onClick = {
                        showAlertBox.value = false
                    },
                ){
                    Text(text = "Cancel")
                }
                TextButton(
                    onClick = {
                        showAlertBox.value = false
                        if (title=="Name"){
                            viewmodel.updateUserData(name = inputText.value, bio = userBio , phone = userPhone, image = userImage)
                        }else{
                            viewmodel.updateUserData(name = userName, bio = inputText.value, phone = userPhone, image = userImage)
                        }

                    },
                ){
                    Text(text = "Save")
                }
            }
        )

    }
}

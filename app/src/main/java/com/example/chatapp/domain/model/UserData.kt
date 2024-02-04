package com.example.chatapp.domain.model

import androidx.compose.material3.Text

data class UserData(
    val name:String? = null,
    val imageUrl:String? = null,
    val bio:String? = null,
    val email:String? = null,
    val uid:String? = null
){
    fun toMap() = mapOf(
        name to name,
        imageUrl to imageUrl,
        bio to bio,
        email to email,
        uid to uid
    )

    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$name",
            "$name",
            "${name?.first()}",
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}


data class ChatData(
    val chatId:String? = "",
    val user1: ChatUser =ChatUser(),
    val user2: ChatUser =ChatUser()
)

data class ChatUser(
    val name:String? = null,
    val imageUrl:String? = null,
    val bio:String? = null,
    val email:String? = null,
    val uid:String? = null
)

val userList = listOf(
    UserData("Modi"),
    UserData("ModiJI"),
    UserData("Bhushan"),
    UserData("Mayur"),
    UserData("Pappu"),
    UserData("Modi"),
    UserData("Modi"),
    UserData("Trump"),
    UserData("Modi"),
    UserData("Raju"),
)
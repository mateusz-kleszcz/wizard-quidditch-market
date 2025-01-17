package com.example.wizardquidditchmarketstore.models.offers

data class MessageRoom(
    val itemId: String,
    val users: UsersRoom,
    val messages: ArrayList<MessagesDetails>? = null
)

data class MessagesDetails(
    val text: String? = null,
    val name: String,
    val photoUrl: String? = null,
    val imageUrl: String? = null,
)

data class UsersRoom(
    val user1: String,
    val user2: String,
)

data class MessageItem(
    val name: String,
    val room: String,
    val user: String
)
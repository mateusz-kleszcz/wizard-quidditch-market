package com.example.wizardquidditchmarketstore.models.offers

data class MessageRoom(
    val users: UsersRoom,
    val messages: List<MessagesDetails>? = null
)

data class MessagesDetails(
    val text: String? = null,
    val name: String? = null,
    val photoUrl: String? = null,
    val imageUrl: String? = null,
)

data class UsersRoom(
    val user1: String,
    val user2: String,
)
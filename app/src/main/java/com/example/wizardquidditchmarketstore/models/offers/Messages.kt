package com.example.wizardquidditchmarketstore.models.offers

data class Messages(
    val user1: String,
    val user2: String,
    val messages: List<MessagesDetails>? = null
)

data class MessagesDetails(
    val text: String? = null,
    val name: String? = null,
    val photoUrl: String? = null,
    val imageUrl: String? = null,
)
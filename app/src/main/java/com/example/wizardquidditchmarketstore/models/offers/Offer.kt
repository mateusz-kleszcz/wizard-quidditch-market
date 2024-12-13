package com.example.wizardquidditchmarketstore.models.offers

import androidx.compose.runtime.MutableState

data class Offer(
    val id: String,
    val name: String,
    val imgSrc: String,
    val price: Int,
)

data class OfferDetails(
    val name: String,
    val imgSrc: String,
    val price: Int,
    val description: String,
)

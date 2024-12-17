package com.example.wizardquidditchmarketstore.models.offers

data class Offer(
    val id: String,
    val name: String,
    val imgSrc: String,
    val price: Int,
)

data class OfferDetailsSave(
    val name: String,
    val imgSrc: String,
    val price: Int,
    val description: String,
)

data class OfferDetails(
    var name: String,
    val imgSrc: String,
    val price: Int,
    val description: String,
    val userId: String,
    var isUserFavourite: Boolean,
    var isSold: Boolean,
)
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
    val longitude: Double,
    val latitude: Double,
    val date: String,
)

data class OfferDetails(
    val name: String,
    val imgSrc: String,
    val price: Int,
    val description: String,
    val userId: String,
    var isUserFavourite: Boolean,
    val longitude: Double,
    val latitude: Double,
    val date: String,
)

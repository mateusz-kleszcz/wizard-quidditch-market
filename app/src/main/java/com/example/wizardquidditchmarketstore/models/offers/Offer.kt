package com.example.wizardquidditchmarketstore.models.offers

import android.net.Uri

data class Offer(
    val id: String,
    val name: String,
    val imgSrc: String,
    val price: Int,
)

data class OfferDetailsSave(
    val name: String,
    val imgSrc: Uri,
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
    val buyers: ArrayList<OfferBuyers>? = null,
    val sold: String? = null
)

data class OfferBuyers(
    val name:String,
    val userId:String,
    val roomId:String? = null
)
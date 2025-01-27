package com.example.wizardquidditchmarketstore.navigation

sealed class Screens(val route: String) {
    object SignIn : Screens("sign_in")
    object SignUp : Screens("sign_up")
    object OffersList : Screens("offers_list")
    object OfferDetails : Screens("offer_details/{id}")
    object AddOffer : Screens("add_offer")
    object Favourites : Screens("favourites")
    object Profile : Screens("profile")
    object Messages : Screens("messages")
    object MRoom : Screens("room/{id}/{name}")
    object Owl : Screens("owl/{id}/{user}")
    object SellScreen : Screens("sell/{id}")
}
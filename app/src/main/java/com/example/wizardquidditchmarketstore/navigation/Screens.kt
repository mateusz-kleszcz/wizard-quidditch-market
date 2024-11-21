package com.example.wizardquidditchmarketstore.navigation

sealed class Screens(val route: String) {
    object SignIn : Screens("sign_in")
    object SignUp : Screens("sign_up")
    object OffersList : Screens("offers_list")
}
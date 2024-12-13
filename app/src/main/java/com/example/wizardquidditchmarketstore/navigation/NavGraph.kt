package com.example.wizardquidditchmarketstore.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.wizardquidditchmarketstore.models.offers.FirebaseViewModel
import com.example.wizardquidditchmarketstore.screens.AddOfferScreen
import com.example.wizardquidditchmarketstore.screens.FavouritesScreen
import com.example.wizardquidditchmarketstore.screens.LoginScreen
import com.example.wizardquidditchmarketstore.screens.OfferDetailsScreen
import com.example.wizardquidditchmarketstore.screens.OffersListScreen
import com.example.wizardquidditchmarketstore.screens.OwlScreen
import com.example.wizardquidditchmarketstore.screens.ProfileScreen
import com.example.wizardquidditchmarketstore.screens.SignUpScreen
import com.example.wizardquidditchmarketstore.viewModels.AddOffersViewModel
import com.example.wizardquidditchmarketstore.viewModels.LoginViewModel
import com.example.wizardquidditchmarketstore.viewModels.SignUpViewModel

@Composable
fun NavGraph (
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    signUpViewModel: SignUpViewModel,
    addOffersViewModel: AddOffersViewModel,
    firebaseViewModel: FirebaseViewModel,
){
    NavHost(
        navController = navController,
        startDestination = Screens.OffersList.route)
    {
        composable(route = Screens.SignIn.route){
            LoginScreen(
                navController = navController,
                viewModel = loginViewModel,
            )
        }
        composable(route = Screens.SignUp.route){
            SignUpScreen(
                navController = navController,
                viewModel=signUpViewModel,
            )
        }
        composable(route = Screens.OffersList.route){
            OffersListScreen(
                navController = navController,
                firebaseViewModel = firebaseViewModel,
            )
        }
        composable(route = Screens.OfferDetails.route +  "?id={id}") { navBackStack ->
            val id: String = navBackStack.arguments?.getString("id") ?: ""
            OfferDetailsScreen(
                navController = navController,
                firebaseViewModel = firebaseViewModel,
                offerId = id
            )
        }
        composable(route = Screens.AddOffer.route){
            AddOfferScreen(
                navController = navController,
                viewModel = addOffersViewModel,
                onCreateOffer = firebaseViewModel::saveOffer,
            )
        }
        composable(route = Screens.Favourites.route){
            FavouritesScreen(
                navController = navController,
            )
        }
        composable(route = Screens.Profile.route){
            ProfileScreen(
                navController = navController,
                firebaseViewModel = firebaseViewModel,
            )
        }
        composable(route = Screens.Owl.route){
            OwlScreen(
                navController = navController,
            )
        }
    }
}
package com.example.wizardquidditchmarketstore.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.wizardquidditchmarketstore.models.offers.FirebaseViewModel
import com.example.wizardquidditchmarketstore.screens.ARScreen
import com.example.wizardquidditchmarketstore.screens.AddOfferScreen
import com.example.wizardquidditchmarketstore.screens.FavouritesScreen
import com.example.wizardquidditchmarketstore.screens.LoginScreen
import com.example.wizardquidditchmarketstore.screens.MessageRoom
import com.example.wizardquidditchmarketstore.screens.MessagesScreen
import com.example.wizardquidditchmarketstore.screens.OfferDetailsScreen
import com.example.wizardquidditchmarketstore.screens.OffersListScreen
import com.example.wizardquidditchmarketstore.screens.ProfileScreen
import com.example.wizardquidditchmarketstore.screens.SellScreen
import com.example.wizardquidditchmarketstore.screens.SignUpScreen
import com.example.wizardquidditchmarketstore.viewModels.AddOffersViewModel
import com.example.wizardquidditchmarketstore.viewModels.LoginViewModel
import com.example.wizardquidditchmarketstore.viewModels.SignUpViewModel
import com.google.android.gms.location.FusedLocationProviderClient

@Composable
fun NavGraph (
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    signUpViewModel: SignUpViewModel,
    addOffersViewModel: AddOffersViewModel,
    firebaseViewModel: FirebaseViewModel,
    fusedLocationClient: FusedLocationProviderClient,
){
    val startDestination =
        if (loginViewModel.getIsUserSignedIn())
            Screens.OffersList.route
        else
            Screens.SignIn.route

    NavHost(
        navController = navController,
        startDestination = Screens.AddOffer.route
    ) {
        composable(route = Screens.MRoom.route + "?id={id}" + "?name={name}") { navBackStack ->
            val id: String = navBackStack.arguments?.getString("id") ?: ""
            val name: String = navBackStack.arguments?.getString("name") ?: ""
            MessageRoom(
                navController = navController,
                firebaseViewModel = firebaseViewModel,
                room = id,
                name = name
            )
        }
        composable(route = Screens.SellScreen.route+ "?id={id}"){ navBackStack ->
            val id: String = navBackStack.arguments?.getString("id") ?: ""
            SellScreen(
                navController = navController,
                firebaseViewModel = firebaseViewModel,
                offerId = id
            )
        }
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
        composable(route = Screens.OfferDetails.route + "?id={id}") { navBackStack ->
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
                fusedLocationClient = fusedLocationClient,
            )
        }
        composable(route = Screens.Favourites.route){
            FavouritesScreen(
                navController = navController,
                firebaseViewModel = firebaseViewModel,
            )
        }
        composable(route = Screens.Profile.route){
            ProfileScreen(
                navController = navController,
                firebaseViewModel = firebaseViewModel,
                authViewModel = loginViewModel
            )
        }
        composable(route = Screens.Messages.route){
            MessagesScreen(
                navController = navController,
                firebaseViewModel = firebaseViewModel,
            )
        }
        composable(route = Screens.Owl.route + "?id={id}" + "?user={user}"){navBackStack ->
            val id: String = navBackStack.arguments?.getString("id") ?: ""
            val userId: String = navBackStack.arguments?.getString("user") ?: ""
            ARScreen(
                navController = navController,
                firebaseViewModel = firebaseViewModel,
                offerId = id,
                userId = userId
            )
        }
    }
}
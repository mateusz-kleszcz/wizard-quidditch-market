package com.example.wizardquidditchmarketstore.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.wizardquidditchmarketstore.screens.LoginScreen
import com.example.wizardquidditchmarketstore.screens.OffersListScreen
import com.example.wizardquidditchmarketstore.screens.SignUpScreen
import com.example.wizardquidditchmarketstore.viewModels.LoginViewModel
import com.example.wizardquidditchmarketstore.viewModels.SignUpViewModel

@Composable
fun NavGraph (
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    signUpViewModel: SignUpViewModel,
){
    NavHost(
        navController = navController,
        startDestination = Screens.SignUp.route)
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
            )
        }
    }
}
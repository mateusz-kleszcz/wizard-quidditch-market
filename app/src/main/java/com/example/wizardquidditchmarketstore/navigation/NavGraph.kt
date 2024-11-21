package com.example.wizardquidditchmarketstore.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.wizardquidditchmarketstore.screens.LoginScreen
import com.example.wizardquidditchmarketstore.screens.SignUpScreen
import com.example.wizardquidditchmarketstore.viewModels.SignUpViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavGraph (
    navController: NavHostController,
    signUpViewModel: SignUpViewModel,
    auth: FirebaseAuth
){
    NavHost(
        navController = navController,
        startDestination = Screens.SignUp.route)
    {
        composable(route = Screens.SignIn.route){
            LoginScreen(navController = navController)
        }
        composable(route = Screens.SignUp.route){
            SignUpScreen(
                navController = navController,
                viewModel=signUpViewModel,
                auth=auth,
            )
        }
    }
}
package com.example.wizardquidditchmarketstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.wizardquidditchmarketstore.models.offers.FirebaseViewModel
import com.example.wizardquidditchmarketstore.models.services.AccountService
import com.example.wizardquidditchmarketstore.models.services.AccountServiceImpl
import com.example.wizardquidditchmarketstore.navigation.NavGraph
import com.example.wizardquidditchmarketstore.ui.theme.WizardQuidditchMarketstoreTheme
import com.example.wizardquidditchmarketstore.viewModels.AddOffersViewModel
import com.example.wizardquidditchmarketstore.viewModels.LoginViewModel
import com.example.wizardquidditchmarketstore.viewModels.SignUpViewModel

class MainActivity : ComponentActivity() {
    private val accountService: AccountService = AccountServiceImpl()
    private val signUpViewModel = SignUpViewModel(accountService)

    private val loginViewModel = LoginViewModel()
    private val addOffersViewModel = AddOffersViewModel()

    private val firebaseViewModel = FirebaseViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            WizardQuidditchMarketstoreTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavGraph(
                        navController = navController,
                        loginViewModel=loginViewModel,
                        signUpViewModel=signUpViewModel,
                        addOffersViewModel=addOffersViewModel,
                        firebaseViewModel=firebaseViewModel,
                    )
                }
            }
        }
    }
}
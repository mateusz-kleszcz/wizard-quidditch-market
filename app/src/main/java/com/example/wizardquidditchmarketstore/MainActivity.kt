package com.example.wizardquidditchmarketstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
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
import com.google.android.gms.location.FusedLocationProviderClient
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {
    private val accountService: AccountService = AccountServiceImpl()
    private val signUpViewModel = SignUpViewModel(accountService)

    private val loginViewModel = LoginViewModel()
    private val addOffersViewModel = AddOffersViewModel()

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val firebaseViewModel = FirebaseViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions -> when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)
                -> {
                // Precise location access granted.
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION,
                false) -> {
                // Only approximate location access granted.
            }
            else -> {
                // No location access granted.
            }
        }}

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }

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
                        fusedLocationClient=fusedLocationClient,
                    )
                }
            }
        }
    }
}
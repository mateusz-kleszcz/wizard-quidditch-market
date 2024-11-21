package com.example.wizardquidditchmarketstore.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import com.example.wizardquidditchmarketstore.models.LoginUiState

class SignInActivity : ComponentActivity() {
    var uiState = mutableStateOf(LoginUiState())
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContent { MakeItSoApp() }
    }
}
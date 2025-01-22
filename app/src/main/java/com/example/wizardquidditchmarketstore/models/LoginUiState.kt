package com.example.wizardquidditchmarketstore.models

data class LoginUiState (
    val email: String = "",
    val password: String = "",
    val errorState: String = "",
)
package com.example.wizardquidditchmarketstore.models

data class SignUpUiState(
    val nick: String = "",
    val email: String = "",
    val password: String = "",
    val errorState: String = "",
)
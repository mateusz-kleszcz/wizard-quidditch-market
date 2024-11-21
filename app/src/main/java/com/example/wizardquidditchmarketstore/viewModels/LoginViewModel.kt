package com.example.wizardquidditchmarketstore.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.wizardquidditchmarketstore.models.LoginUiState

class LoginViewModel: ViewModel() {
    var uiState = mutableStateOf(LoginUiState())
        private set

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }
}
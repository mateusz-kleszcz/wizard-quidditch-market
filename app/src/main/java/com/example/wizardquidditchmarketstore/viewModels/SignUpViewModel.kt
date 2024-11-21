package com.example.wizardquidditchmarketstore.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.wizardquidditchmarketstore.models.SignUpUiState
import com.example.wizardquidditchmarketstore.models.services.AccountService
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
) : ViewModel() {
    var uiState = mutableStateOf(SignUpUiState())
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    fun onSignUpClick() {
        accountService.linkAccount(
            uiState.value.email,
            uiState.value.password,
        ) { error ->
            if (error == null) {
                Log.d("TAG", "success")
            } else {
                Log.d("TAG", "error")
            }
        }
    }
}

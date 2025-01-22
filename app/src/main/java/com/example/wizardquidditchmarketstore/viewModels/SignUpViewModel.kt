package com.example.wizardquidditchmarketstore.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.wizardquidditchmarketstore.models.SignUpUiState
import com.example.wizardquidditchmarketstore.models.services.AccountService
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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
    private val errorState
        get() = uiState.value.errorState

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onSignUpClick(onResult: () -> Unit) {
        if (email == "") {
            uiState.value = uiState.value.copy(errorState = "You need to provide an email")
        }
        else if (password == "") {
            uiState.value = uiState.value.copy(errorState = "You need to provide a password")
        } else {
            Firebase.auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    uiState.value = uiState.value.copy(email = "")
                    uiState.value = uiState.value.copy(password = "")
                    uiState.value = uiState.value.copy(errorState = "")
                    onResult()
                }
                .addOnFailureListener { error ->
                    uiState.value = uiState.value.copy(errorState = error.message.toString())
                }
        }
    }
}

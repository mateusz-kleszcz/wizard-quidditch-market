package com.example.wizardquidditchmarketstore.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.wizardquidditchmarketstore.models.LoginUiState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel: ViewModel() {
    var uiState = mutableStateOf(LoginUiState())
        private set

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun login(onResult: () -> Unit) {
        if (uiState.value.email == "") {
            uiState.value = uiState.value.copy(errorState = "You need to provide an email")
        }
        else if (uiState.value.password == "") {
            uiState.value = uiState.value.copy(errorState = "You need to provide a password")
        } else {
            Firebase.auth.signInWithEmailAndPassword(uiState.value.email, uiState.value.password)
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

    fun singOut(onResult: () -> Unit) {
        try {
            Firebase.auth.signOut()
            onResult()
        } catch(e: Error) {
            Log.d("ERROR", e.toString())
        }
    }

    fun getIsUserSignedIn(): Boolean {
        return Firebase.auth.currentUser != null
    }
}
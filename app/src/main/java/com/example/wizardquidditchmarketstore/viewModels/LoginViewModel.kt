package com.example.wizardquidditchmarketstore.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.wizardquidditchmarketstore.models.LoginUiState
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
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

    fun login(onResult: (Throwable?) -> Unit) {
        try {
            Firebase.auth.signInWithEmailAndPassword(uiState.value.email, uiState.value.password)
                .addOnCompleteListener { onResult(it.exception) }
        } catch(e: Error) {
            Log.d("ERROR", e.toString())
        }
    }
}
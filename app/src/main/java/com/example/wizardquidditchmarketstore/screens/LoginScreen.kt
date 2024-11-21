package com.example.wizardquidditchmarketstore.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.navigation.NavController
import com.example.wizardquidditchmarketstore.common.EmailField
import com.example.wizardquidditchmarketstore.viewModels.LoginViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun LoginScreen(navController: NavController) {
//    val uiState by viewModel.uiState

    fun login() {
        try {
        Firebase.auth.signInWithEmailAndPassword("email@email.com", "password")
            .addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    Log.d("SUCCESS", "SUCCESS")
                } else {
                    Log.d("ERROR", task.exception.toString())
                }
            }
        } catch(e: Error) {
            Log.d("ERROR", e.toString())
        }
    }

    Column() {
//        EmailField(uiState.email, viewModel::onEmailChange)
        Button(onClick = { login() }) {
            Text("CLICK")
        }
    }
}
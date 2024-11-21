package com.example.wizardquidditchmarketstore.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.wizardquidditchmarketstore.common.EmailField
import com.example.wizardquidditchmarketstore.common.PasswordField
import com.example.wizardquidditchmarketstore.common.RepeatPasswordField
import com.example.wizardquidditchmarketstore.models.SignUpUiState
import com.example.wizardquidditchmarketstore.viewModels.SignUpViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel,
    auth: FirebaseAuth
) {
    val uiState by viewModel.uiState

    fun onSignUpClick() {
        Firebase.auth.createUserWithEmailAndPassword(uiState.email, uiState.password)
            .addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    Log.d("SUCCESS", "SUCCESS")
                } else {
                    Log.d("ERROR", task.result.toString())
                }
            }
    }

    SignUpScreenContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRepeatPasswordChange = viewModel::onRepeatPasswordChange,
        onSignUpClick = ::onSignUpClick,
    )
}

@Composable
fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    uiState: SignUpUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        EmailField(uiState.email, onEmailChange)
        PasswordField(uiState.password, onPasswordChange)
        RepeatPasswordField(uiState.repeatPassword, onRepeatPasswordChange)
        Button(onClick=onSignUpClick) {
            Text("Sign Up")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    val uiState = SignUpUiState(
        email = "email@test.com"
    )

    SignUpScreenContent(
        uiState = uiState,
        onEmailChange = { },
        onPasswordChange = { },
        onRepeatPasswordChange = { },
        onSignUpClick = { },
    )
}

package com.example.wizardquidditchmarketstore.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wizardquidditchmarketstore.common.EmailField
import com.example.wizardquidditchmarketstore.common.PasswordField
import com.example.wizardquidditchmarketstore.models.SignUpUiState
import com.example.wizardquidditchmarketstore.navigation.Screens
import com.example.wizardquidditchmarketstore.viewModels.SignUpViewModel

import com.example.wizardquidditchmarketstore.R.string as AppText

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel,
) {
    val uiState by viewModel.uiState

    SignUpScreenContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onSignUpClick = viewModel::onSignUpClick,
        navController = navController
    )
}

@Composable
fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    uiState: SignUpUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignUpClick: (onResult: () -> Unit) -> Unit,
    navController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(
            uiState.email,
            onEmailChange,
            modifier = modifier.padding(bottom = 24.dp)
        )
        PasswordField(
            uiState.password,
            onPasswordChange,
            modifier = modifier.padding(bottom = 24.dp)
        )
        Button(onClick= { onSignUpClick {
            navController.navigate(Screens.OffersList.route)
        } }) {
            Text(stringResource(AppText.sign_up))
        }
        TextButton(onClick={ navController.navigate(Screens.SignIn.route) }) {
            Text(stringResource(AppText.sign_in))
        }
        Text(
            uiState.errorState,
            color = Color.Red
        )
    }
}
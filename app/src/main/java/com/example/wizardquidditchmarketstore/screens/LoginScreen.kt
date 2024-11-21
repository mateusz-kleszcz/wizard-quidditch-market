package com.example.wizardquidditchmarketstore.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.wizardquidditchmarketstore.common.EmailField
import com.example.wizardquidditchmarketstore.common.PasswordField
import com.example.wizardquidditchmarketstore.navigation.Screens
import com.example.wizardquidditchmarketstore.viewModels.LoginViewModel

import com.example.wizardquidditchmarketstore.R.string as AppText

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(uiState.email, viewModel::onEmailChange)
        PasswordField(uiState.password, viewModel::onPasswordChange)
        Button(onClick = { viewModel.login { error ->
            if (error == null) {
                navController.navigate(Screens.OffersList.route)
            } else {
                Log.d("ERROR", "ERROR")
            }
        } }) {
            Text(stringResource(AppText.sign_in))
        }

        Button(onClick={ navController.navigate(Screens.SignUp.route) }) {
            Text(stringResource(AppText.sign_up))
        }
    }
}
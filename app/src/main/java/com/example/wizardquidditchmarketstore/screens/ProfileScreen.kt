package com.example.wizardquidditchmarketstore.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wizardquidditchmarketstore.R
import com.example.wizardquidditchmarketstore.common.BasicField
import com.example.wizardquidditchmarketstore.common.OfferItem
import com.example.wizardquidditchmarketstore.common.WizardNavigationBar
import com.example.wizardquidditchmarketstore.models.offers.FirebaseViewModel
import com.example.wizardquidditchmarketstore.navigation.Screens
import com.example.wizardquidditchmarketstore.viewModels.LoginViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    firebaseViewModel: FirebaseViewModel,
    authViewModel: LoginViewModel,
    modifier: Modifier = Modifier
) {
    firebaseViewModel.fetchUserOffers()
    firebaseViewModel.fetchUserSettings()
    val offersList = firebaseViewModel.userOffersList

    Scaffold(
        topBar = { WizardNavigationBar(navController) }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 120.dp, start = 24.dp, end = 24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.fillMaxSize()
            ) {
                BasicField(
                    value = firebaseViewModel.userNick,
                    onNewValue = { firebaseViewModel.userNick = it },
                    placeholder = "Your nick",
                    modifier
                        .width(240.dp)
                        .padding(end = 24.dp)
                )
                Button(
                    onClick = { firebaseViewModel.updateUserSettings(firebaseViewModel.userNick) },
                ) {
                    Text(stringResource(R.string.update_user_nick))
                }
            }
            Button(
                onClick = { authViewModel.singOut {
                    navController.navigate(Screens.SignIn.route)
                }}
            ) {
                Text(stringResource(R.string.sign_out))
            }
            Text(
                text = stringResource(R.string.my_offers_label),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp),)
            offersList.forEach { offer ->
                OfferItem(offer, navController)
            }
            Button(
                onClick = { navController.navigate(Screens.AddOffer.route) }
            ) {
                Text(stringResource(R.string.add_offer))
            }
        }
    }
}
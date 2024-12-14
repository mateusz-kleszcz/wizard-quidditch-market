package com.example.wizardquidditchmarketstore.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wizardquidditchmarketstore.R
import com.example.wizardquidditchmarketstore.common.OfferItem
import com.example.wizardquidditchmarketstore.common.WizardNavigationBar
import com.example.wizardquidditchmarketstore.models.offers.FirebaseViewModel
import com.example.wizardquidditchmarketstore.navigation.Screens

@Composable
fun ProfileScreen(
    navController: NavController,
    firebaseViewModel: FirebaseViewModel,
    modifier: Modifier = Modifier
) {
    firebaseViewModel.fetchUserOffers()
    firebaseViewModel.fetchUserSettings()
    val offersList = firebaseViewModel.userOffersList

    var checked by remember { mutableStateOf(firebaseViewModel.userNotifications) }

    Scaffold(
        topBar = { WizardNavigationBar(navController) }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 120.dp)
        ) {
            Button(
                onClick = { navController.navigate(Screens.AddOffer.route) }
            ) {
                Text(stringResource(R.string.add_offer))
            }
            offersList.forEach { offer ->
                OfferItem(offer, navController)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxSize()
            ) {
                Switch(
                    checked = checked,
                    onCheckedChange = {
                        firebaseViewModel.updateUserSettings()
                        checked = it
                    }
                )
                Text("Notifications: " + firebaseViewModel.userNotifications)
            }
        }
    }
}
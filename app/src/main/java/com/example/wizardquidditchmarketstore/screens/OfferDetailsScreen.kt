package com.example.wizardquidditchmarketstore.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.wizardquidditchmarketstore.models.offers.FirebaseViewModel

@Composable
fun OfferDetailsScreen(
    navController: NavController,
    firebaseViewModel: FirebaseViewModel,
    offerId: String,
    modifier: Modifier = Modifier
) {
    firebaseViewModel.fetchOfferDetails(offerId)
    val offerDetails = firebaseViewModel.offerDetails ?: return

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(offerDetails.name)
        Text(offerDetails.description)
    }
}
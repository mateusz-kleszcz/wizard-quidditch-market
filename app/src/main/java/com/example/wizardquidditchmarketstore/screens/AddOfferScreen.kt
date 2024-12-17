package com.example.wizardquidditchmarketstore.screens

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.wizardquidditchmarketstore.common.BasicField
import com.example.wizardquidditchmarketstore.common.NumberField
import com.example.wizardquidditchmarketstore.common.WizardNavigationBar
import com.example.wizardquidditchmarketstore.models.offers.OfferDetailsSave
import com.example.wizardquidditchmarketstore.viewModels.AddOffersViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.LocalDate

import com.example.wizardquidditchmarketstore.R.string as AppText

@SuppressLint("MissingPermission")
@Composable
fun AddOfferScreen(
    navController: NavController,
    viewModel: AddOffersViewModel,
    modifier: Modifier = Modifier,
    onCreateOffer: (offer: OfferDetailsSave) -> Unit,
    fusedLocationClient: FusedLocationProviderClient,
) {
    val uiState by viewModel.uiState
    var latitude by remember { mutableDoubleStateOf(0.0) }
    var longitude by remember { mutableDoubleStateOf(0.0) }

    fusedLocationClient.lastLocation
        .addOnSuccessListener { location: Location? ->
            latitude = location?.latitude ?: 0.0
            longitude = location?.longitude ?: 0.0
        }

    Scaffold(
        topBar = { WizardNavigationBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicField(uiState.name, viewModel::onNameChange, "Name of the offer")
            BasicField(uiState.imgSrc, viewModel::onImgSrcChange, "Image source")
            NumberField(uiState.price, viewModel::onPriceChange, "Price")
            BasicField(uiState.description, viewModel::onDescriptionChange, "Description")
            Button(onClick = {
                val currentMoment = Clock.System.now()
                val currentDate = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault()).date
                onCreateOffer(
                    OfferDetailsSave(
                        uiState.name,
                        uiState.imgSrc,
                        uiState.price,
                        uiState.description,
                        longitude,
                        latitude,
                        currentDate.toString(),
                    )
                )
            }) {
                Text(stringResource(AppText.add_offer))
            }
        }
    }
}
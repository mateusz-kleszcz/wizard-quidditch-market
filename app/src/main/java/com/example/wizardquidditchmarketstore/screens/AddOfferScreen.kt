package com.example.wizardquidditchmarketstore.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.wizardquidditchmarketstore.common.BasicField
import com.example.wizardquidditchmarketstore.common.NumberField
import com.example.wizardquidditchmarketstore.models.offers.OfferDetails
import com.example.wizardquidditchmarketstore.navigation.Screens
import com.example.wizardquidditchmarketstore.viewModels.AddOffersViewModel

import com.example.wizardquidditchmarketstore.R.string as AppText

@Composable
fun AddOfferScreen(
    navController: NavController,
    viewModel: AddOffersViewModel,
    modifier: Modifier = Modifier,
    onCreateOffer: (offer: OfferDetails) -> Unit
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
        BasicField(uiState.name, viewModel::onNameChange, "Name of the offer")
        BasicField(uiState.imgSrc, viewModel::onImgSrcChange, "Image source")
        NumberField(uiState.price, viewModel::onPriceChange, "Price")
        BasicField(uiState.description, viewModel::onDescriptionChange, "Description")
        Button(onClick={ onCreateOffer(OfferDetails(uiState.name, uiState.imgSrc, uiState.price, uiState.description)) }) {
            Text(stringResource(AppText.add_offer))
        }
        Button(onClick={ navController.navigate(Screens.OffersList.route) }) {
            Text(stringResource(AppText.offers_list))
        }
    }
}
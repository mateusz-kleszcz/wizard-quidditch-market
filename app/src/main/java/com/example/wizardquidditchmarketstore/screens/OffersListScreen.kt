package com.example.wizardquidditchmarketstore.screens

import coil.compose.AsyncImage
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.request.ImageRequest
import com.example.wizardquidditchmarketstore.R
import com.example.wizardquidditchmarketstore.common.WizardNavigationBar
import com.example.wizardquidditchmarketstore.models.offers.Offer
import com.example.wizardquidditchmarketstore.navigation.Screens

@Composable
fun OffersListScreen(
    navController: NavController,
    offersList: List<Offer?>,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { WizardNavigationBar(navController) }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 120.dp)
        ) {
            offersList.forEach { offer ->
                OfferItem(offer, navController)
            }
        }
    }
}

@Composable
fun OfferItem(
    offer: Offer?,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    if (offer == null) {
        return
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp)
            .border(border = BorderStroke(width = 2.dp, Color.LightGray))
            .clickable(onClick = {
                navController.navigate(
                    Screens.OfferDetails.route
                        .replace(
                            oldValue = "{id}",
                            newValue = offer.id
                        )
                )
            })
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(offer.imgSrc)
                .crossfade(true)
                .build(),
            contentDescription = "A photo",
            contentScale = ContentScale.Crop,
            modifier = modifier.size(100.dp)
        )
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            Text(
                offer.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp),
            )
            Text(
                stringResource(R.string.price_label) + offer.price.toString(),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
            )
        }
    }
}
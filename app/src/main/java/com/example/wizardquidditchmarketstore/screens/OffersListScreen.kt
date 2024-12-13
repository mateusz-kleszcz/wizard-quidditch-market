package com.example.wizardquidditchmarketstore.screens

import coil.compose.AsyncImage
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.request.ImageRequest
import com.example.wizardquidditchmarketstore.R
import com.example.wizardquidditchmarketstore.models.offers.Offer
import com.example.wizardquidditchmarketstore.navigation.Screens

@Composable
fun OffersListScreen(
    navController: NavController,
    offersList: List<Offer?>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.offers_list))
        offersList.forEach { offer ->
            OfferItem(offer, navController)
        }
        Button(onClick={ navController.navigate(Screens.AddOffer.route) }) {
            Text(stringResource(R.string.add_offer))
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
            .border(border = BorderStroke(width = 1.dp, Color.LightGray))
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
            modifier = modifier.size(100.dp)
        )
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            Text(
                offer.name,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp),
            )
            Text(
                offer.price.toString(),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp),
            )
        }
    }
}
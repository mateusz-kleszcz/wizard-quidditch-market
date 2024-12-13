package com.example.wizardquidditchmarketstore.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.wizardquidditchmarketstore.R
import com.example.wizardquidditchmarketstore.models.offers.Offer
import com.example.wizardquidditchmarketstore.navigation.Screens

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
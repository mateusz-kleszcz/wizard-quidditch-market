package com.example.wizardquidditchmarketstore.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.wizardquidditchmarketstore.common.WizardNavigationBar
import com.example.wizardquidditchmarketstore.models.offers.FirebaseViewModel
import com.example.wizardquidditchmarketstore.navigation.Screens

@Composable
fun OfferDetailsScreen(
    navController: NavController,
    firebaseViewModel: FirebaseViewModel,
    offerId: String,
    modifier: Modifier = Modifier
) {
    firebaseViewModel.fetchOfferDetails(offerId)
    var offerDetails = firebaseViewModel.offerDetails ?: return

    Scaffold(
        topBar = { WizardNavigationBar(navController) }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 132.dp)
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = modifier
                    .fillMaxSize()
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(offerDetails.imgSrc)
                        .crossfade(true)
                        .build(),
                    contentDescription = offerDetails.name,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .size(200.dp)
                        .padding(end = 16.dp)
                )
                Column() {
                    Text(
                        offerDetails.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(stringResource(R.string.price_label) + offerDetails.price.toString())
                    if (offerDetails.isUserFavourite) {
                        IconButton(
                            onClick = { firebaseViewModel.removeFromFavourites(offerId) },
                        ) {
                            Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favourites")
                        }
                    } else {
                        IconButton(
                            onClick = { firebaseViewModel.addToFavourites(offerId) },
                        ) {
                            Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "Favourites")
                        }
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = modifier
                    .padding(vertical = 24.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    stringResource(R.string.description),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(offerDetails.description)
            }
            var current_user = firebaseViewModel.get_auth()
            if (offerDetails.userId == current_user.currentUser?.uid.toString()){
                Button(
                    onClick = { }
                ) {
                    Text("Sell item")
                }
                if(offerDetails.isSold == true){
                    Button(
                        onClick = { }
                    ) {
                        Text("Send Owl")
                    }
                }
            }else{
                Button(
                    onClick = { navController.navigate(
                        Screens.MRoom.route.replace(
                                oldValue = "{id}",
                                newValue = offerDetails.name
                            )
                    )}
                ) {
                    Text("Send message")
                }
            }


        }
    }
}
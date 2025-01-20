package com.example.wizardquidditchmarketstore.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wizardquidditchmarketstore.R
import com.example.wizardquidditchmarketstore.common.WizardNavigationBar
import com.example.wizardquidditchmarketstore.models.offers.FirebaseViewModel
import com.example.wizardquidditchmarketstore.models.offers.OfferBuyers
import com.example.wizardquidditchmarketstore.models.offers.OfferDetails
import com.example.wizardquidditchmarketstore.navigation.Screens

private var selectedUser by  mutableStateOf<OfferBuyers?>(null)


@Composable
fun SellScreen(
    navController: NavController,
    firebaseViewModel: FirebaseViewModel,
    modifier: Modifier = Modifier,
    offerId: String
){
    val offerDetails = firebaseViewModel.offerDetails ?: return
    Scaffold(
        topBar = { WizardNavigationBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            Text(
                text = "Available Buyers",
                modifier = modifier.padding(bottom = 16.dp)
            )

            ListUsers(offerDetails)
            SellButton(navController,offerId)
        }

    }
}


@Composable
fun ListUsers(offerDetails: OfferDetails) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Text("Selected User:"+ selectedUser?.name)

        if(offerDetails.buyers!= null){
            offerDetails.buyers.forEach { buyer ->
                val isSelected = buyer == selectedUser
                var colors = RadioButtonDefaults.colors(
                    selectedColor = colorResource(id = R.color.purple_200),
                    unselectedColor = colorResource(id = R.color.purple_700)
                )
                Row {
                    RadioButton(
                        selected = isSelected,
                        onClick = {selectedUser = buyer},
                        colors = colors
                    )
                    Text(buyer.name)
                }

            }
        }

    }
}

@Composable
fun SellButton(
    navController: NavController,
    offerId: String){
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        if(selectedUser!=null){
            Button(
                onClick = { navController.navigate(
                    Screens.Owl.route
                    .replace(oldValue = "{id}",newValue =  offerId)
                    .replace(oldValue = "{user}", newValue = selectedUser!!.userId)
                ) },
            ) {
                Text("Sell")
            }
        }
    }

}
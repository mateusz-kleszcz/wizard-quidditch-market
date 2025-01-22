package com.example.wizardquidditchmarketstore.screens

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.wizardquidditchmarketstore.common.BasicField
import com.example.wizardquidditchmarketstore.common.CameraCompose
import com.example.wizardquidditchmarketstore.common.NumberField
import com.example.wizardquidditchmarketstore.common.WizardNavigationBar
import com.example.wizardquidditchmarketstore.models.offers.OfferDetailsSave
import com.example.wizardquidditchmarketstore.navigation.Screens
import com.example.wizardquidditchmarketstore.viewModels.AddOffersViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

import com.example.wizardquidditchmarketstore.R.string as AppText


@SuppressLint("MissingPermission")
@Composable
fun AddOfferScreen(
    navController: NavController,
    viewModel: AddOffersViewModel,
    modifier: Modifier = Modifier,
    onCreateOffer: (offer: OfferDetailsSave, onResult: () -> Unit) -> Unit,
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

    val context = LocalContext.current

    val file by remember { mutableStateOf(context.createImageFile()) }
    val uri by remember {
        mutableStateOf(
            FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file
            )
        )
    }

    Scaffold(
        topBar = { WizardNavigationBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 140.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicField(uiState.name, viewModel::onNameChange, "Name of the offer")
            NumberField(uiState.price, viewModel::onPriceChange, "Price")
            BasicField(uiState.description, viewModel::onDescriptionChange, "Description")
            CameraCompose(
                imgSrc = uri,
                onImgSrcChange = viewModel::onImgSrcChange
            )
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
                    ),
                    { navController.navigate(Screens.OffersList.route) }
                )
            }) {
                Text(stringResource(AppText.add_offer))
            }
            if (uiState.imgSrc.path?.isNotEmpty() == true) {
                AsyncImage(
                    model = uiState.imgSrc,
                    contentDescription = "photo",
                    modifier = Modifier
                        .size(300.dp)
                        .padding(20.dp)
                )
            }
        }
    }
}

fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
    return image
}
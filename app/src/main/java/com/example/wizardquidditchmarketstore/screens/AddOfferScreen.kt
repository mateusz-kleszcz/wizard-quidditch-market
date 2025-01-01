package com.example.wizardquidditchmarketstore.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
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
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Objects
import coil.compose.AsyncImage
import coil.compose.AsyncImage
import coil.request.ImageRequest

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
    //photo
    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.getPackageName() + ".provider", file
    )
    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }
    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            capturedImageUri = uri
        }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        cameraLauncher.launch(uri)
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
            NumberField(uiState.price, viewModel::onPriceChange, "Price")
            BasicField(uiState.description, viewModel::onDescriptionChange, "Description")
            Button(onClick = {
                val permissionCheckResult =
                    ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)
                if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                    cameraLauncher.launch(uri)
                } else {
                    // Request a permission
                    permissionLauncher.launch(android.Manifest.permission.CAMERA)
                }
            }) {
                Text(text = "Photo")
            }
            Button(onClick = {
                val currentMoment = Clock.System.now()
                val currentDate = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault()).date
                onCreateOffer(
                    OfferDetailsSave(
                        uiState.name,
                        capturedImageUri.toString(),
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

            //trying to show the pic after taking photo
            if (capturedImageUri.path?.isNotEmpty() == true) {
                Text(capturedImageUri.toString())
                coil3.compose.AsyncImage(
                    model = capturedImageUri,
                    contentDescription = "photo",
                )
            }

        }
    }
}

fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        externalCacheDir /* directory */
    )
    return image
}
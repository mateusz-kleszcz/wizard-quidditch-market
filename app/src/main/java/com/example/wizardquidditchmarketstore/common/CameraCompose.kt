package com.example.wizardquidditchmarketstore.common

import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun CameraCompose(
    imgSrc: Uri,
    onImgSrcChange: (newValue: Uri) -> Unit,
    modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {}
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        cameraLauncher.launch(imgSrc)
    }
    Button(onClick = {
        val permissionCheckResult =
            ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)
        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
            onImgSrcChange(imgSrc)
            cameraLauncher.launch(imgSrc)
        } else {
            permissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }) {
        Text(text = "Photo")
    }
}
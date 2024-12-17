package com.example.wizardquidditchmarketstore.screens

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.wizardquidditchmarketstore.common.WizardNavigationBar

@Composable
fun OwlScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { WizardNavigationBar(navController) }
    ) { innerPadding ->
        Text("OWL")
    }
}
package com.example.wizardquidditchmarketstore.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wizardquidditchmarketstore.common.WizardNavigationBar

@Composable
fun MessageRoom(
    navController: NavController,
    userName: String,
    modifier: Modifier = Modifier,
) {
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
        ) {Text(
            userName,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )}
    }
}
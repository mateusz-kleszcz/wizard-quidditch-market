package com.example.wizardquidditchmarketstore.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wizardquidditchmarketstore.common.WizardNavigationBar
import com.example.wizardquidditchmarketstore.models.offers.ChatViewModel
import com.example.wizardquidditchmarketstore.models.offers.FirebaseViewModel
import com.example.wizardquidditchmarketstore.models.offers.MessagesDetails


@Composable
fun MessageRoom(
    navController: NavController,
    firebaseViewModel: FirebaseViewModel,
    room: String,
    modifier: Modifier = Modifier,
) {
    firebaseViewModel.fetchMessageRoom(room)
    val ola = firebaseViewModel.currentMessageRoom
    val chatViewModel = ChatViewModel(room,firebaseViewModel)

    val messages = chatViewModel.currentRoom.collectAsState()

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
            MessageList(messages.value)
        }
    }
}

@Composable
fun MessageList(messages: List<MessagesDetails>) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        if(messages!=null){
            messages.forEach { message ->
                Text(
                    text = "${message.name}: ${message.text}",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }

    }
}
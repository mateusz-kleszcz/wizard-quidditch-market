package com.example.wizardquidditchmarketstore.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
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
    name: String,
    modifier: Modifier = Modifier,
) {
    val chatViewModel = ChatViewModel(room,firebaseViewModel)
    val messages = chatViewModel.currentRoom.collectAsState()

    val nick = firebaseViewModel.userNick

    DisposableEffect(Unit) {
        onDispose {
            chatViewModel.onCleared()
        }
    }
    Scaffold(
        topBar = { WizardNavigationBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                MessageList(messages.value)
            }

            MessageInput { message ->
                chatViewModel.sendMessage(message)
            }
        }
    }
}

@Composable
fun MessageList(messages: List<MessagesDetails>) {

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        if(messages!=null){
            messages.forEach { message ->
                Text(
                    text = "${message.name}:",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(4.dp)
                )
                Text(
                    text = "${message.text}",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(4.dp)
                )


            }
        }

    }
}

@Composable
fun MessageInput(onSend: (String) -> Unit) {
    var message by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        BasicTextField(
            value = message,
            onValueChange = { message = it },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
                .border(1.dp, MaterialTheme.colors.primary, MaterialTheme.shapes.small)
                .padding(8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(onSend = {
                if (message.isNotEmpty()) {
                    onSend(message)
                    message = ""
                }
            })
        )

        Button(
            onClick = {
                if (message.isNotEmpty()) {
                    onSend(message)
                    message = ""
                }
            }
        ) {
            Text("Send")
        }
    }
}

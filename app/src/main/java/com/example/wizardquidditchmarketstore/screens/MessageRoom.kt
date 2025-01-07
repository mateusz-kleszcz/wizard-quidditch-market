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
import com.example.wizardquidditchmarketstore.models.offers.FirebaseViewModel


@Composable
fun MessageRoom(
    navController: NavController,
    firebaseViewModel: FirebaseViewModel,
    room: String,
    modifier: Modifier = Modifier,
) {
    firebaseViewModel.fetchMessageRoom(room)
    val currentRoom = firebaseViewModel.currentMessageRoom ?: return
    val user = firebaseViewModel.get_auth().currentUser?.uid.toString()
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
            if (user == currentRoom.users.user1) {
                Text(
                    currentRoom.users.user2,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    currentRoom.users.user1,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            if(currentRoom.messages!= null){
                for( message in currentRoom.messages){
                    Text(message.name)
                }
            }

        }
    }
}
package com.example.wizardquidditchmarketstore.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wizardquidditchmarketstore.R
import com.example.wizardquidditchmarketstore.models.offers.FirebaseViewModel
import com.example.wizardquidditchmarketstore.models.offers.MessageItem
import com.example.wizardquidditchmarketstore.navigation.Screens

@Composable
fun MessageItem(
    messageItem: MessageItem?,
    navController: NavController,
    modifier: Modifier = Modifier
) {


    if (messageItem==null){
        return
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp)
            .border(border = BorderStroke(width = 2.dp, Color.LightGray))
            .clickable(onClick = {
                navController.navigate(
                    Screens.MRoom.route
                        .replace(
                            oldValue = "{id}",
                            newValue = messageItem.room
                        ).replace(
                            oldValue = "{name}",
                            newValue = messageItem.name
                        )
                )
            })
    ){
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            Text(
                "${messageItem.name} | ${messageItem.userName}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp),
            )
        }
    }
}
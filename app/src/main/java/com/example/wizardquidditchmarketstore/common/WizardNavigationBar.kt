package com.example.wizardquidditchmarketstore.common

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wizardquidditchmarketstore.navigation.Screens
import com.example.wizardquidditchmarketstore.R

@Composable
fun WizardNavigationBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route
    val colors = NavigationBarItemDefaults.colors(
        selectedIconColor = Color(0xffb5eefd),
        selectedTextColor = Color(0xffb5eefd),
        indicatorColor = Color.Transparent
    )
    NavigationBar(
        containerColor = Color(0xff262d3a),
        modifier = modifier
            .height(120.dp),
    ) {
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.AutoMirrored.Filled.List, contentDescription = "Offers List") },
            selected = currentRoute == Screens.OffersList.route,
            label = { Text(stringResource(R.string.offers_list)) },
            onClick = {
                navController.navigate(Screens.OffersList.route) {
                    launchSingleTop = true
                    restoreState = true
                }
            },
            colors = colors,
            modifier = modifier.padding(top = 20.dp)
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorite") },
            selected = currentRoute == Screens.Favourites.route,
            label = { Text(stringResource(R.string.favourites)) },
            onClick = {
                navController.navigate(Screens.Favourites.route) {
                    launchSingleTop = true
                    restoreState = true
                }
            },
            colors = colors,
            modifier = modifier.padding(top = 20.dp)
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Person, contentDescription = "Profile") },
            selected = currentRoute == Screens.Profile.route,
            label = { Text(stringResource(R.string.profile)) },
            onClick = {
                navController.navigate(Screens.Profile.route) {
                    launchSingleTop = true
                    restoreState = true
                }
            },
            colors = colors,
            modifier = modifier.padding(top = 20.dp)
        )
    }
}
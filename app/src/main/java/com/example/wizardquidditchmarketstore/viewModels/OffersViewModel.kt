package com.example.wizardquidditchmarketstore.viewModels

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.wizardquidditchmarketstore.models.offers.OfferDetailsSave

class AddOffersViewModel : ViewModel() {
    var uiState = mutableStateOf(OfferDetailsSave("", Uri.EMPTY, 0, "", 0.0, 0.0, ""))
        private set

    private val name
        get() = uiState.value.name
    private val imgSrc
        get() = uiState.value.imgSrc
    private val price
        get() = uiState.value.price
    private val description
        get() = uiState.value.description

    fun onNameChange(newValue: String) {
        uiState.value = uiState.value.copy(name = newValue)
    }

    fun onImgSrcChange(newValue: Uri) {
        uiState.value = uiState.value.copy(imgSrc = newValue)
    }

    fun onPriceChange(newValue: Int) {
        uiState.value = uiState.value.copy(price = newValue)
    }

    fun onDescriptionChange(newValue: String) {
        uiState.value = uiState.value.copy(description = newValue)
    }

    fun clearData() {
        uiState.value = uiState.value.copy(name = "")
        uiState.value = uiState.value.copy(imgSrc = Uri.EMPTY)
        uiState.value = uiState.value.copy(price = 0)
        uiState.value = uiState.value.copy(description = "")
    }
}

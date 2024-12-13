package com.example.wizardquidditchmarketstore.models.offers

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wizardquidditchmarketstore.navigation.Screens
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class FirebaseViewModel: ViewModel() {
    private var db = Firebase.database
    private var auth = Firebase.auth
    private var itemsRef = db.getReference("Offers")

    var offersList by mutableStateOf(ArrayList<Offer>())
    var userOffersList by mutableStateOf(ArrayList<Offer>())
    var offerDetails: OfferDetails? = null

    fun fetchAllOffers() {
        viewModelScope.launch {
            try {
                val snapshot = itemsRef.get().await()
                val offers = ArrayList<Offer>()
                for (ds in snapshot.getChildren()) {
                    val id = ds.key ?: ""
                    val name = ds.child("name").getValue<String>() ?: ""
                    val imgSrc = ds.child("imgSrc").getValue<String>() ?: ""
                    val price = ds.child("price").getValue<Int>() ?: 0
                    val offer = Offer(id, name, imgSrc, price)
                    offers.add(offer)
                }
                offersList = offers
            } catch (e: Exception) {
                Log.d("ERROR", e.toString())
                e.printStackTrace()
            }
        }
    }

    fun fetchUserOffers() {
        viewModelScope.launch {
            try {
                val snapshot = itemsRef.get().await()
                val offers = ArrayList<Offer>()
                for (ds in snapshot.getChildren()) {
                    val id = ds.key ?: ""
                    val name = ds.child("name").getValue<String>() ?: ""
                    val imgSrc = ds.child("imgSrc").getValue<String>() ?: ""
                    val price = ds.child("price").getValue<Int>() ?: 0
                    val userId = ds.child("userId").getValue<String>() ?: ""
                    if (userId == auth.currentUser?.uid.toString()) {
                        val offer = Offer(id, name, imgSrc, price)
                        offers.add(offer)
                    }
                }
                userOffersList = offers
            } catch (e: Exception) {
                Log.d("ERROR", e.toString())
                e.printStackTrace()
            }
        }
    }

    fun fetchOfferDetails(id: String) {
        viewModelScope.launch {
            try {
                val snapshot = itemsRef.child(id).get().await()
                val name = snapshot.child("name").getValue<String>() ?: ""
                val imgSrc = snapshot.child("imgSrc").getValue<String>() ?: ""
                val price = snapshot.child("price").getValue<Int>() ?: 0
                val description = snapshot.child("description").getValue<String>() ?: ""
                val userId = snapshot.child("userId").getValue<String>() ?: ""
                val offer = OfferDetails(name, imgSrc, price, description, userId)
                offerDetails = offer
            } catch (e: Exception) {
                Log.d("ERROR", e.toString())
                e.printStackTrace()
            }
        }
    }

    fun saveOffer(offerSave: OfferDetailsSave) {
        viewModelScope.launch {
            try {
                val newItemRef = itemsRef.push()
                val offerDetails = OfferDetails(
                    name = offerSave.name,
                    imgSrc = offerSave.imgSrc,
                    price = offerSave.price,
                    description = offerSave.description,
                    userId = auth.currentUser?.uid.toString()
                )
                newItemRef.setValue(offerDetails).await()
                fetchAllOffers()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
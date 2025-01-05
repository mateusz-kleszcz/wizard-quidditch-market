package com.example.wizardquidditchmarketstore.models.offers

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class FirebaseViewModel(): ViewModel() {
    private var db = Firebase.database
    private var auth = Firebase.auth
    private var itemsRef = db.getReference("Offers")
    private var usersRef = db.getReference("Users")
    private var messagesRoomRef = db.getReference("MessagesRoom")

    var offersList by mutableStateOf(ArrayList<Offer>())
    var favourtiesOffersList by mutableStateOf(ArrayList<Offer>())
    var userOffersList by mutableStateOf(ArrayList<Offer>())
    var offerDetails by mutableStateOf<OfferDetails?>(null)
    var userNotifications by mutableStateOf(false)

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

    fun fetchUserFavourites() {
        viewModelScope.launch {
            try {
                val userId = auth.currentUser?.uid.toString()
                val favouritesSnapshot = usersRef
                    .child(userId)
                    .child("favourites")
                    .get()
                    .await()
                val favouritesIds = ArrayList<String>()
                for (ds in favouritesSnapshot.getChildren()) {
                    val offerId = ds.getValue<String>() ?: ""
                    favouritesIds.add(offerId)
                }
                val offers = ArrayList<Offer>()
                for (id in favouritesIds) {
                    val offerSnapshot = itemsRef.child(id).get().await()
                    val offerId = offerSnapshot.key ?: ""
                    val name = offerSnapshot.child("name").getValue<String>() ?: ""
                    val imgSrc = offerSnapshot.child("imgSrc").getValue<String>() ?: ""
                    val price = offerSnapshot.child("price").getValue<Int>() ?: 0
                    val offer = Offer(offerId, name, imgSrc, price)
                    offers.add(offer)
                }
                favourtiesOffersList = offers
            } catch (e: Exception) {
                Log.d("ERROR", e.toString())
                e.printStackTrace()
            }
        }
    }

    fun addToFavourites(offerId: String) {
        viewModelScope.launch {
            try {
                val userId = auth.currentUser?.uid.toString()
                val favouritesRef = usersRef
                    .child(userId)
                    .child("favourites")
                    .push()
                val copy = offerDetails?.copy()
                copy?.isUserFavourite = true
                offerDetails = copy
                favouritesRef.setValue(offerId).await()
            } catch (e: Exception) {
                Log.d("ERROR", e.toString())
                e.printStackTrace()
            }
        }
    }

    fun removeFromFavourites(offerId: String) {
        viewModelScope.launch {
            try {
                val userId = auth.currentUser?.uid.toString()
                val offerRef = usersRef.child(userId).child("favourites")
                val snapshot = offerRef.get().await()
                var idToRemove = ""
                for (ds in snapshot.getChildren()) {
                    val id = ds.getValue<String>() ?: ""
                    if (id == offerId) {
                        idToRemove = ds.key ?: ""
                    }
                }
                offerRef.child(idToRemove).removeValue()
                val copy = offerDetails?.copy()
                copy?.isUserFavourite = false
                offerDetails = copy
            } catch (e: Exception) {
                Log.d("ERROR", e.toString())
                e.printStackTrace()
            }
        }
    }

    fun fetchUserSettings() {
        viewModelScope.launch {
            try {
                val userId = auth.currentUser?.uid.toString()
                val snapshot = usersRef.child(userId).child("notifications").get().await()
                val notifications = snapshot.getValue<Boolean>() ?: false
                userNotifications = notifications
            } catch (e: Exception) {
                Log.d("ERROR", e.toString())
                e.printStackTrace()
            }
        }
    }

    fun updateUserSettings() {
        viewModelScope.launch {
            try {
                val userId = auth.currentUser?.uid.toString()
                val notificationsRef = usersRef.child(userId).child("notifications")
                notificationsRef.setValue(!userNotifications).await()
                userNotifications = !userNotifications
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
                val latitude = snapshot.child("latitude").getValue<Double>() ?: 0.0
                val longitude = snapshot.child("longitude").getValue<Double>() ?: 0.0
                val date = snapshot.child("date").getValue<String>() ?: ""
                val userId = snapshot.child("userId").getValue<String>() ?: ""
                val isSold = snapshot.child("isSold").getValue<Boolean>() ?: false
                var isUserFavourite = false
                val favouritesSnapshot = usersRef
                    .child(auth.currentUser?.uid.toString())
                    .child("favourites")
                    .get()
                    .await()
                for (ds in favouritesSnapshot.getChildren()) {
                    val offerId = ds.getValue<String>() ?: ""
                    if (offerId == id) {
                        isUserFavourite = true
                    }
                }
                offerDetails = OfferDetails(
                    name,
                    imgSrc,
                    price,
                    description,
                    userId,
                    isUserFavourite,
                    longitude,
                    latitude,
                    date,
                    isSold,
                )
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
                    latitude = offerSave.latitude,
                    longitude = offerSave.longitude,
                    date = offerSave.date,
                    userId = auth.currentUser?.uid.toString(),
                    isUserFavourite = false,
                    isSold = false,
                )
                newItemRef.setValue(offerDetails).await()
                fetchAllOffers()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun saveMessageRoom(userRoom: UsersRoom){
        viewModelScope.launch {
            try {
                var bool = false
                val userId = auth.currentUser?.uid.toString()
                val userRoomRef = usersRef.child(userId).child("rooms").get().await()
                for (ds in userRoomRef.getChildren()) {
                    val id = ds.getValue<String>() ?: ""
                    val snapshot2 = messagesRoomRef.child(id).child("users").get().await()
                    val users = UsersRoom(
                        user1 = snapshot2.child("user1").getValue<String>() ?: "",
                        user2 = snapshot2.child("user2").getValue<String>() ?: ""
                    )

                    if(users.user1 == userRoom.user1 && users.user2 == userRoom.user2){
                        println("-------------")
                        println("AAAAAAAAAAAAAAA")
                        bool = true
                    }

                }
                if(!bool){
                    val newItemRef = messagesRoomRef.push()
                    val messageRoom = MessageRoom(
                        users = userRoom,
                        messages = null
                    )
                    newItemRef.setValue(messageRoom).await()
                    val roomsRef = usersRef
                        .child(auth.currentUser?.uid.toString())
                        .child("rooms")
                        .push()
                    roomsRef.setValue(newItemRef.key)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun sellItem(offerId: String) {
        viewModelScope.launch {
            try {
                itemsRef
                    .child(offerId)
                    .child("isSold")
                    .setValue(true)
                    .await()
                fetchOfferDetails(offerId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun get_auth(): FirebaseAuth {
        return auth
    }

    


}
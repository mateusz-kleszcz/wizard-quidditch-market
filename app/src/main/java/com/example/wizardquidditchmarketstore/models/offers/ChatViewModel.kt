package com.example.wizardquidditchmarketstore.models.offers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.getValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    id:String,
    firebaseViewModel: FirebaseViewModel,
): ViewModel() {
    private val database = firebaseViewModel.getMessagesRef(id)
    private var _currentRoom = MutableStateFlow<List<MessagesDetails>>(emptyList())


    val currentRoom = _currentRoom.asStateFlow()

    init {
        listenForMessages()
    }

    private fun listenForMessages(){

        database.addChildEventListener( object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val name = snapshot.child("name").getValue<String>() ?: ""
                val text = snapshot.child("text").getValue<String>() ?: ""
                val newMessage = MessagesDetails(
                    text = text,
                    name = name
                )
                viewModelScope.launch {
                    _currentRoom.value += newMessage
                }

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }



}
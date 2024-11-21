package com.example.wizardquidditchmarketstore.models.services

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class AccountServiceImpl (): AccountService {

    override fun createAnonymousAccount(onResult: (Throwable?) -> Unit) {
        Firebase.auth.signInAnonymously()
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit) {
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun linkAccount(email: String, password: String, onResult: (Throwable?) -> Unit) {
        Log.d("TAG", "TAG")
        Firebase.auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    Log.d("SUCCESS", "SUCCESS")
                } else {
                    Log.d("ERROR", "ERROR")
                }
            }
    }

    companion object {
        private const val LINK_ACCOUNT_TRACE = "linkAccount"
    }
}
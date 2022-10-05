package com.cs3450.dansfrappesraps.ui.repositories

import com.cs3450.dansfrappesraps.ui.models.User
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.lang.RuntimeException

class SignInException(message: String?): RuntimeException(message)
class SignUpException(message: String?): RuntimeException(message)

object UserRepository {

    private var userCache = User()

    suspend fun createUser(email: String, password: String, name: String) {
        try {
            Firebase.auth.createUserWithEmailAndPassword(
                email,
                password
            ).await()
            val doc = Firebase.firestore.collection("users").document()
            val user = User(
                name = name,
                email = email,
                userId = getCurrentUserId(),
                id = doc.id,
                manager = false,
                employee = false
            )
            doc.set(user).await()
            userCache = user
        } catch (e: FirebaseAuthException) {
            throw SignUpException(e.message)
        }
    }
    suspend fun loginUser(email: String, password: String) {
        try {
            var user= User();
            Firebase.auth.signInWithEmailAndPassword(
                email,
                password
            ).await()
            val snapshot = Firebase.firestore
                .collection("users")
                .whereEqualTo("userId", getCurrentUserId())
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    for (document in documentSnapshot) {
                        user = document.toObject<User>()
                    }
                }
                .await()
            userCache = user
        } catch (e: FirebaseAuthException) {
            throw SignInException(e.message)
        }
    }

    fun getCurrentUserId(): String? {
        return Firebase.auth.currentUser?.uid
    }

    fun isUserLoggedIn(): Boolean {
        return getCurrentUserId() != null
    }

    fun signOutUser() {
        Firebase.auth.signOut()
    }

    fun isUserManager(): Boolean {
        try {
            //TODO cache
            return userCache.manager!!
        } catch (_: Exception) {

        }
        return false
    }

    fun isUserEmployee(): Boolean {
        try {
            return userCache.employee!!
        } catch (_: Exception) {

        }
        return false
    }
}
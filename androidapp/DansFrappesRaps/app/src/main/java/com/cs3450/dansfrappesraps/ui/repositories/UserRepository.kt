package com.cs3450.dansfrappesraps.ui.repositories

import android.util.Log
import com.cs3450.dansfrappesraps.ui.models.Cart
import com.cs3450.dansfrappesraps.ui.models.Drink
import com.cs3450.dansfrappesraps.ui.models.User
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class SignInException(message: String?): RuntimeException(message)
class SignUpException(message: String?): RuntimeException(message)

object UserRepository {

    private var userCache = User()
    private val allUsersCache = mutableListOf<User>()

    suspend fun createUser(email: String, password: String, name: String, isManager: Boolean = false, isEmployee: Boolean = false) {
        try {
            Firebase.auth.createUserWithEmailAndPassword(
                email,
                password
            ).await()
            val testDoc = Firebase.firestore.collection("users").whereEqualTo("email", email)
            var doc = Firebase.firestore.collection("users").document()
            if (testDoc.get().await().isEmpty) {
                doc.set(
                    User(
                        name = name,
                        email = email,
                        userId = getCurrentUserId(),
                        id = doc.id,
                        manager = isManager,
                        employee = isEmployee,
                        balance = 0.00
                    )
                )
            } else {
                doc = testDoc.get().await().documents[0].reference
                doc.set(
                    User(
                        name = name,
                        email = email,
                        userId = getCurrentUserId(),
                        id = doc.id,
                        manager = isManager,
                        employee = isEmployee,
                        balance = 0.00
                    )
                )
            }
            userCache = doc.get().await().toObject()!!
        } catch (e: FirebaseAuthException) {
            throw SignUpException(e.message)
        }
    }

    suspend fun createDifferentUser(email: String, name: String, isManager: Boolean = false, isEmployee: Boolean = false, payRate: Double = 0.00) {
        try {
            val doc = Firebase.firestore.collection("users").document()
            val user = User(
                name = name,
                email = email,
                userId = "",
                id = doc.id,
                manager = isManager,
                employee = isEmployee,
                balance = 0.00,
                payRate = payRate
            )
            doc.set(user).await()
            allUsersCache.add(user)
        } catch (e: FirebaseAuthException) {
            throw SignUpException(e.message)
        }
    }

    suspend fun updateUser(user: User) {
        try {
            Firebase.firestore.collection("users").document(user.id!!).set(user).await()
            val oldUserIndex = allUsersCache.indexOfFirst { it.id == user.id }
            allUsersCache[oldUserIndex] = user
            if (userCache.id == user.id) {
                userCache = user
            }
        } catch (_: Exception) {
        }
    }

    suspend fun loginUser(email: String, password: String) {
        try {
            var user: User
            Firebase.auth.signInWithEmailAndPassword(
                email,
                password
            ).await()
            Firebase.firestore
                .collection("users")
                .whereEqualTo("userId", getCurrentUserId())
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    for (document in documentSnapshot) {
                        user = document.toObject()
                        userCache = user
                    }
                }
                .await()
        } catch (e: FirebaseAuthException) {
            throw SignInException(e.message)
        }
    }

    suspend fun setUserCache() {
        try {
            var user: User
            Firebase.firestore
                .collection("users")
                .whereEqualTo("userId", getCurrentUserId())
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    for (document in documentSnapshot) {
                        user = document.toObject()
                        userCache = user
                    }
                }
                .await()
            updateFavorite(userCache)
        } catch (e: FirebaseAuthException) {
            throw SignInException(e.message)
        }
    }

    suspend fun getAllUsers(): List<User> {
        try {
            if (allUsersCache.isEmpty()) {
                val snapshot = Firebase.firestore
                    .collection("users")
                    .get()
                    .await()
                allUsersCache.addAll(snapshot.toObjects())
            }

            return allUsersCache
        } catch (e: FirebaseAuthException) {
            throw SignInException(e.message)
        }
    }

    suspend fun removeFavorite(drink: Drink){
        userCache.favorites?.remove(drink)
        updateFavorite(userCache)
    }
    suspend fun addFavorite(drink: Drink){

        userCache.favorites?.add(drink)
        Log.e("Error", userCache.favorites.toString())
        updateFavorite(userCache)
    }

    suspend fun updateFavorite(user: User){
        try {
            Firebase.firestore.collection("users").document(user.id!!).set(user).await()
        } catch (_: Exception) {

        }
    }

    suspend fun deleteUser(user: User) {
        try {
            // Only delete the user from Firestore, not from authentication
            // That way they can still sign in and access their account, but it is wiped
            Firebase.firestore
                .collection("users")
                .document(user.id!!)
                .delete()
                .await()
        } catch (e: FirebaseAuthException) {
            throw SignInException(e.message)
        }
    }

    suspend fun editAuthUser(email: String) {
        try {
            Firebase.auth.currentUser!!.updateEmail(email).await()
        } catch (e: FirebaseAuthException) {
            throw SignInException(e.message)
        }
    }

    suspend fun sendToBank(user: User) {
        try {
            updateUser(user)
        } catch (_: Exception) {
        }
    }

    fun getCurrentUser(): User {
        return userCache
    }

    private fun getCurrentUserId(): String? {
        return Firebase.auth.currentUser?.uid
    }

    fun isUserLoggedIn(): Boolean {
        return getCurrentUserId() != null
    }

    fun signOutUser() {
        try {
            userCache = User()
            Firebase.auth.signOut()
            OrdersRepository.clearCurrentOrder()
        } catch (_: Exception){}
    }

    fun isUserManager(): Boolean {
        try {
            //TODO cache
            return userCache.manager!!
        } catch (_: Exception) {

        }
        return false
    }

    fun userBalance(): Double? {
        return userCache.balance
    }

    suspend fun addUserBalance(addedBalance : Double){
        try {
            val user = userCache
            user.balance = user.balance?.plus(addedBalance)
            val db = Firebase.firestore
            db.collection("users")
                .document(userCache.id!!)
                .set(user)
                .await()
            userCache = user
        } catch (_: Exception) {
        }
    }
    suspend fun subtractUserBalance(subtractedBalance: Double){
        try{
            val user = userCache
            user.balance = user.balance?.minus(subtractedBalance)
            val db = Firebase.firestore
            db.collection("users")
                .document(userCache.id!!)
                .set(user)
                .await()
            userCache = user
        } catch(_: Exception){
        }
    }

    fun isUserEmployee(): Boolean {
        try {
            return userCache.employee!!
        } catch (_: Exception) {
        }
        return false
    }
}
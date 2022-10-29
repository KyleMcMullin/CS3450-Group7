package com.cs3450.dansfrappesraps.ui.repositories

import com.cs3450.dansfrappesraps.ui.models.Cart
import com.cs3450.dansfrappesraps.ui.models.Drink
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object CartRepository {
    private var cartCache = Cart()
    private var allCartCache = mutableListOf<Cart>()
    suspend fun makeCart(drink:Drink){
        try {
            val allCarts = getAllCarts()
            for(cart in allCarts){
                if(cart.userId == UserRepository.getCurrentUser().userId){
                    cartCache = cart
                    addDrink(drink)
                    return
                }
            }
            val doc = Firebase.firestore.collection("cart").document()
            cartCache.id = doc.id
            cartCache.userId = UserRepository.getCurrentUser().userId
            if (cartCache.drinks == null) {
                cartCache.drinks = mutableListOf(drink)
            }
            doc.set(cartCache).await()
        }catch(_: Exception){
        }
    }
    suspend fun addDrink(drink: Drink){
        if(cartCache.drinks == null) {
            makeCart(drink)
        }
        else{
            cartCache.drinks!!.add(drink)
            updateCart(cartCache)
        }

    }
    suspend fun getAllCarts(): List<Cart>{
        try {
            if (allCartCache.isEmpty()) {
                val snapshot = Firebase.firestore
                    .collection("cart")
                    .get()
                    .await()
                allCartCache.addAll(snapshot.toObjects())
            }
            return allCartCache
        } catch (e: FirebaseAuthException) {
            throw SignInException(e.message)
        }
    }
    suspend fun updateCart(cart: Cart){
        try {
            val doc = Firebase.firestore.collection("cart").document(cart.id!!)
            doc.set(cart).await()
        } catch (_: Exception) {
        }
    }
    suspend fun deleteCart(){
        try {
            Firebase.firestore
                .collection("cart")
                .document(cartCache.id!!)
                .delete()
                .await()
        } catch (e: FirebaseAuthException) {
            throw SignInException(e.message)
        }
    }

}
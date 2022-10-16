package com.cs3450.dansfrappesraps.ui.repositories

import com.cs3450.dansfrappesraps.ui.models.Drink
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object DrinksRepository {
    private var drinksCache = mutableListOf<Drink>()

    suspend fun getDrinks(): MutableList<Drink> {
        if (drinksCache.isEmpty()) {
            drinksCache.addAll(getAllDrinks())
        }
        return drinksCache
    }

    suspend fun newDrink(drink: Drink) {
        try {
            val doc = Firebase.firestore.collection("menu").document()
            drink.id = doc.id
            doc.set(drink).await()
            drinksCache.add(drink)
        } catch (_: Exception) {
        }
    }

    suspend fun getAllDrinks(): List<Drink> {
        try {
            val snapshot = Firebase.firestore.collection("menu")
                .get()
                .await()
            return snapshot.toObjects()
        } catch (_: Exception) {
            return mutableListOf()
        }
    }

    suspend fun deleteDrink(drink: Drink) {
        try {
            Firebase.firestore.collection("menu").document(drink.id!!).delete().await()
        } catch (_: Exception) {
        }
    }
}
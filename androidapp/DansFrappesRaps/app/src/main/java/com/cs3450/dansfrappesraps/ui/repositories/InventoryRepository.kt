package com.cs3450.dansfrappesraps.ui.repositories

import com.cs3450.dansfrappesraps.ui.models.Ingredient
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object InventoryRepository {
    private var InventoryCache = mutableListOf<Ingredient>()

    suspend fun getInventory(): MutableList<Ingredient> {
        try {
            if (InventoryCache.isEmpty()) {
                val snapshot = Firebase.firestore.collection("inventory")
                    .get()
                    .await()
                InventoryCache.addAll(snapshot.toObjects())
            }
            return InventoryCache
        } catch (_: Exception) {
            return mutableListOf()
        }
    }

    suspend fun addInventory(name: String, quantity: Int, PPU: Double) {
        try {

            val doc = Firebase.firestore.collection("inventory").document()
            doc.set(
                Ingredient(
                    name = name,
                    PPU = PPU,
                    quantity = quantity,
                    id = doc.id
                )
            ).await()
            InventoryCache.add(
                Ingredient(
                    name = name,
                    PPU = PPU,
                    quantity = quantity,
                    id = doc.id
                )
            )
        } catch (_: Exception) {
        }
    }

    suspend fun editInventory(id: String, name: String, quantity: Int, PPU: Double) {
        try {
            val doc = Firebase.firestore.collection("inventory").document(id)
            doc.set(
                Ingredient(
                    name = name,
                    PPU = PPU,
                    quantity = quantity,
                    id = id
                )
            ).await()
            InventoryCache.removeIf { it.id == id }
            InventoryCache.add(
                Ingredient(
                    name = name,
                    PPU = PPU,
                    quantity = quantity,
                    id = id
                )
            )
        } catch (_: Exception) {
        }
    }

    suspend fun deleteInventory(id: String) {
        try {
            val doc = Firebase.firestore.collection("inventory").document(id)
            doc.delete().await()
            InventoryCache.removeIf { it.id == id }
        } catch (_: Exception) {
        }
    }

}
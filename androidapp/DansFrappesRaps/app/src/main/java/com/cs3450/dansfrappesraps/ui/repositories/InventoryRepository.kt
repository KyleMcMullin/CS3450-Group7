package com.cs3450.dansfrappesraps.ui.repositories

import com.cs3450.dansfrappesraps.ui.models.Inventory
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object InventoryRepository {
    private var InventoryCache = mutableListOf<Inventory>()

    suspend fun getInventory(): MutableList<Inventory> {
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
                Inventory(
                    name = name,
                    PPU = PPU,
                    quantity = quantity,
                    id = doc.id
                )
            ).await()
            InventoryCache.add(
                Inventory(
                    name = name,
                    PPU = PPU,
                    quantity = quantity,
                    id = doc.id
                )
            )
            IngredientsRepository.refresh()
        } catch (_: Exception) {
        }
    }

    suspend fun editInventory(id: String, name: String, quantity: Int, PPU: Double) {
        try {
            val doc = Firebase.firestore.collection("inventory").document(id)
            doc.set(
                Inventory(
                    name = name,
                    PPU = PPU,
                    quantity = quantity,
                    id = id
                )
            ).await()
            InventoryCache.removeIf { it.id == id }
            InventoryCache.add(
                Inventory(
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
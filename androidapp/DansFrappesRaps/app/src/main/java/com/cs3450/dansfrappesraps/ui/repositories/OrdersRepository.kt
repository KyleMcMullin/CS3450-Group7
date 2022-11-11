package com.cs3450.dansfrappesraps.ui.repositories

import com.cs3450.dansfrappesraps.ui.models.Order
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object OrdersRepository {
    var ordersCache = Order()
    private var allOrdersCache = mutableListOf<Order>()
    suspend fun getOrder(): Order? {
        val allOrders = getAllOrders()
        for(order in allOrders){
            if(order.status == UserRepository.getCurrentUser().userId){
                ordersCache = order
                return ordersCache
            }
        }
        return null
    }
    suspend fun getAllOrders(): List<Order> {
        try {
            if (allOrdersCache.isEmpty()) {
                val snapshot = Firebase.firestore
                    .collection("orders")
                    .get()
                    .await()
                allOrdersCache.addAll(snapshot.toObjects())
            }
            return allOrdersCache
        } catch (e: FirebaseAuthException) {
            throw SignInException(e.message)
        }
    }
}
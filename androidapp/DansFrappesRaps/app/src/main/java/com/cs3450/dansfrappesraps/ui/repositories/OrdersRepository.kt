package com.cs3450.dansfrappesraps.ui.repositories

import com.cs3450.dansfrappesraps.ui.models.Drink
import com.cs3450.dansfrappesraps.ui.models.Order
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object OrdersRepository {
    var orderCache: Order? = null
    private var allOrdersCache = mutableListOf<Order>()

    /*
    What are use cases for this one?
    If it is to get a specific order then it may need to have parameter of order id
        (thinking of the employee drink screen)
     */
    suspend fun getOrder(): Order? {
        val allOrders = getAllOrders()
        for(order in allOrders){
            if(order.status == UserRepository.getCurrentUser().userId){
                orderCache = order
                return orderCache
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

    fun getUnplacedOrder(): Order {
        if (orderCache == null) {
            val order = Order(
                userId = UserRepository.getCurrentUser().userId,
                drinks = mutableListOf()
            )
            orderCache = order
        }
        return orderCache!!
    }

    fun addDrinkToOrder(drink: Drink) {
        if (orderCache == null) {
            getUnplacedOrder()
        }
        orderCache!!.drinks!!.add(drink)
    }

    suspend fun placeOrder() {
        try {
            val doc = Firebase.firestore.collection("orders").document()
            orderCache?.id = doc.id
            orderCache?.status = "placed"
            doc.set(orderCache!!).await()
            orderCache = null
        } catch (e: FirebaseAuthException) {
            throw SignInException(e.message)
        }
    }
}
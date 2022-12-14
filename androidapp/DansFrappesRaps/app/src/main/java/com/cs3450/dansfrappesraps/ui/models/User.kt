package com.cs3450.dansfrappesraps.ui.models

data class User(
    val id: String? = null,
    var userId: String? = null,
    var name: String? = null,
    var email: String? = null,
    var employee: Boolean? = false,
    var manager: Boolean? = false,
    var favorites: MutableList<Drink>? = null,
    var balance: Double? = 0.00,
    var payRate: Double? = 0.00,
    var hours: Int = 0
)
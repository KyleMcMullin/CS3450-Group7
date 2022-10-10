package com.cs3450.dansfrappesraps.ui.models

data class User(
    val id: String? = null,
    val userId: String? = null,
    val name: String? = null,
    val email: String? = null,
    val employee: Boolean? = false,
    val manager: Boolean? = false,
    val favorites: List<String>? = null,
    var balance: Double? = 0.00
)
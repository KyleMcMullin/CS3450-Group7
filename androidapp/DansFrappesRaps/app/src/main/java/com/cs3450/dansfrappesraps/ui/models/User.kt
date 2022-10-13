package com.cs3450.dansfrappesraps.ui.models

data class User(
    val id: String? = null,
    var userId: String? = null,
    var name: String? = null,
    var email: String? = null,
    var employee: Boolean? = false,
    var manager: Boolean? = false,
    val favorites: List<String>? = null,
    var balance: Double? = 0.00
)
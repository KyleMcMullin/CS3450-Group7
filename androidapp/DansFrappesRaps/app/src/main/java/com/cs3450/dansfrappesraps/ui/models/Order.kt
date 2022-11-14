package com.cs3450.dansfrappesraps.ui.models

data class Order(
    var userId: String? = null,
    var id: String? = null,
    var status: String? = null,
    var drinks: MutableList<Drink>? = null
)
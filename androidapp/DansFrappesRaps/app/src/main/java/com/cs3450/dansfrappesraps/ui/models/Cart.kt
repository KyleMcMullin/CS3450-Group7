package com.cs3450.dansfrappesraps.ui.models

data class Cart(
    var id: String? = null,
    var userId: String? = null,
    var drinks: MutableList<Drink>? = null
)
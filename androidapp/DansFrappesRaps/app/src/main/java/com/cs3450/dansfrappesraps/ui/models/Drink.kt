package com.cs3450.dansfrappesraps.ui.models

data class Drink(
    var id: String? = null,
    val name: String? = null,
    val ingredients: List<Ingredient>? = null,
    val image: String? = null,
    // for cart
    var quantity: Int? = 0
    )
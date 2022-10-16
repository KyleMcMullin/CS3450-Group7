package com.cs3450.dansfrappesraps.ui.repositories

import com.cs3450.dansfrappesraps.ui.models.Ingredient

object IngredientsRepository {
    private var ingredientsCache = mutableListOf<Ingredient>()

    suspend fun getIngredients(): MutableList<Ingredient> {
        if (ingredientsCache.isEmpty()) {
            setIngredients()
        }
        return ingredientsCache
    }

    suspend fun setIngredients() {
        for (inventory in InventoryRepository.getInventory()) {
            ingredientsCache.add(Ingredient(inventory = inventory, count = 0))
            ingredientsCache.removeIf { it.inventory?.name == "Small" || it.inventory?.name == "Medium" || it.inventory?.name == "Large" }
        }
    }

    suspend fun refresh() {
        if (ingredientsCache.isNotEmpty()) {
            ingredientsCache.clear()
            setIngredients()
        }
    }
}
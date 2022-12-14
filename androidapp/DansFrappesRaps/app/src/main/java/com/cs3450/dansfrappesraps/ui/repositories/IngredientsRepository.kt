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
            ingredientsCache.removeIf { it.inventory?.type == "Drink"}
        }
    }

    suspend fun refresh() {
        if (ingredientsCache.isNotEmpty()) {
            ingredientsCache.clear()
            setIngredients()
        }
    }

    suspend fun getSizes(): MutableList<Ingredient> {
        var returnList: MutableList<Ingredient> = mutableListOf()
        for (inventory in InventoryRepository.getInventory()) {
            if (inventory.type == "Drink") {
                returnList.add(Ingredient(inventory = inventory, count = 0))
            }
        }
        return returnList
    }
}
package com.jakubvanko.incloset.data.repository

import com.jakubvanko.incloset.domain.model.ClothingCategory
import com.jakubvanko.incloset.domain.model.ClothingItem

enum class SettingsAction {
    EditCategory,
    EditItem,
    CreateCategory,
    CreateItem,
}

class ClothingRepository {

    fun getClothingCategories(): Pair<List<ClothingItem>, List<ClothingCategory>> {
        return getItemsMock()
    }

    fun generateId(): String {
        return System.currentTimeMillis().toString()
    }
}

fun getItemsMock(): Pair<List<ClothingItem>, List<ClothingCategory>> {
    val clothingCategories = mutableListOf(
        ClothingCategory("1", "T-shirts", 1),
        ClothingCategory("2", "Trousers", 1),
        ClothingCategory("3", "Underwear", 2)
    )
    val clothingItems = mutableListOf(
        ClothingItem("1", "Black T-shirt", 1, 1,"1", "With a cat", null),
        ClothingItem("2", "Red T-shirt", 1, 1, "1", "With stars", null),
        ClothingItem("3", "Dark green T-shirt", 1, 1, "1", null, null),
        ClothingItem("4", "Basic T-shirt", 3, 4, "1", null, null),
        ClothingItem("5", "Uncommon T-shirt", 0, 2, "1", null, null),
        ClothingItem("6", "Whitewashed jeans", 1, 1, "2", "Informal", null),
        ClothingItem("7", "Black jeans", 2, 3, "2", "For everyday usage", null),
        ClothingItem("8", "Underwear", 8, 12, "3", "Boxers and briefs", null)
    )
    return Pair(clothingItems, clothingCategories)
}
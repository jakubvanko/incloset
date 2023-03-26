package com.jakubvanko.incloset.data.repository

import com.jakubvanko.incloset.domain.model.ClothingCategory
import com.jakubvanko.incloset.domain.model.ClothingItem

class ClothingRepository {

    fun getClothingCategories(): Pair<List<ClothingItem>, List<ClothingCategory>> {
        return getItemsMock()
    }
}

fun getItemsMock(): Pair<List<ClothingItem>, List<ClothingCategory>> {
    val clothingCategories = mutableListOf(
        ClothingCategory("1", "T-shirts", 1, "T-shirts with a short sleeve"),
        ClothingCategory("2", "Trousers", 1, "Basic jeans"),
        ClothingCategory("3", "Underwear", 2, "Boxer briefs")
    )
    val clothingItems = mutableListOf(
        ClothingItem("1", "Black T-shirt", 1, 1, clothingCategories[0], "With a cat", null),
        ClothingItem("2", "Red T-shirt", 1, 1, clothingCategories[0], "With stars", null),
        ClothingItem("3", "Dark green T-shirt", 1, 1, clothingCategories[0], null, null),
        ClothingItem("4", "Basic T-shirt", 3, 4, clothingCategories[0], null, null),
        ClothingItem("5", "Basic T-shirt", 0, 2, clothingCategories[0], null, null),
        ClothingItem("6", "Whitewashed jeans", 1, 1, clothingCategories[1], "Informal", null),
        ClothingItem("7", "Black jeans", 2, 3, clothingCategories[1], "For everyday usage", null),
        ClothingItem("8", "Underwear", 8, 12, clothingCategories[2], "Boxers and briefs", null)
    )
    return Pair(clothingItems, clothingCategories)
}
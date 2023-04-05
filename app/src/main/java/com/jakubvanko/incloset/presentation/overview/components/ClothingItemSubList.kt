package com.jakubvanko.incloset.presentation.overview.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import com.jakubvanko.incloset.domain.model.ClothingCategory
import com.jakubvanko.incloset.domain.model.ClothingItem
import com.jakubvanko.incloset.presentation.ClothingViewModel

fun getClothingItems(
    category: ClothingCategory, clothingViewModel: ClothingViewModel
): List<ClothingItem> {
    val clothingItems =
        clothingViewModel.clothingItems.filter { it.category == category && it.count != 0 }
            .sortedBy { it.name }
    if (clothingViewModel.isInEditMode) {
        return clothingItems.plus(clothingViewModel.clothingItems.filter { it.category == category && it.count == 0 }
            .sortedBy { it.name })
    }
    return clothingItems
}

@Composable
fun ClothingItemSubList(
    category: ClothingCategory,
    clothingViewModel: ClothingViewModel,
) {
    Column {
        getClothingItems(category, clothingViewModel).forEach {
            ClothingItemListItem(
                item = it,
                clothingViewModel = clothingViewModel
            )
            Divider()
        }
    }
}
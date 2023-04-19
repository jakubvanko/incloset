package com.jakubvanko.incloset.presentation.overview.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jakubvanko.incloset.domain.model.ClothingCategory
import com.jakubvanko.incloset.domain.model.ClothingItem
import com.jakubvanko.incloset.presentation.ClothingViewModel

private fun getClothingItems(
    category: ClothingCategory, clothingViewModel: ClothingViewModel
): List<ClothingItem> {
    val clothingItems =
        clothingViewModel.clothingItems.filter { it.categoryId == category.id && it.count != 0 }
            .sortedBy { it.name }
    if (clothingViewModel.isInEditMode) {
        return clothingItems.plus(clothingViewModel.clothingItems.filter { it.categoryId == category.id && it.count == 0 }
            .sortedBy { it.name })
    }
    return clothingItems
}

@Composable
private fun EmptyItemBox() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(text = "No items", color = Color.Gray)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClothingListForCategory(
    category: ClothingCategory,
    clothingViewModel: ClothingViewModel,
) {
    val clothingItems = getClothingItems(category, clothingViewModel)

    Column {
        clothingItems.forEach { item ->
            val isTooltipVisible =
                clothingViewModel.itemTooltipVisible.getOrDefault(item.id, false)

            ListItem(
                modifier = Modifier.clickable { clothingViewModel.flipItemViewExpanded(item) },
                headlineText = { Text(text = item.name) },
                supportingText = {
                    if (item.description != null) {
                        Text(item.description)
                    }
                },
                trailingContent = {
                    Text(
                        text = if (clothingViewModel.isInEditMode || isTooltipVisible)
                            "${item.count}/${item.totalCount}"
                        else item.count.toString()
                    )
                },
                tonalElevation = if (item.count == 0) 10.dp else 0.dp
            )
            if (isTooltipVisible) {
                ItemTooltip(item = item, clothingViewModel = clothingViewModel)
            }
            Divider()
        }
        if (clothingItems.isEmpty()) {
            EmptyItemBox()
            Divider()
        }
    }
}
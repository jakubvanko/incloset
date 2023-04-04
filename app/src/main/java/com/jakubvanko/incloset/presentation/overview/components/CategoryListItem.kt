package com.jakubvanko.incloset.presentation.overview.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.jakubvanko.incloset.domain.model.ClothingCategory
import com.jakubvanko.incloset.domain.model.ClothingItem
import com.jakubvanko.incloset.presentation.ClothingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryListItem(
    category: ClothingCategory,
    categoryItems: List<ClothingItem>,
    clothingViewModel: ClothingViewModel,
    isEditing: Boolean,
    shouldStartExpanded: Boolean
) {
    val (isExpanded, setExpanded) = remember { mutableStateOf(shouldStartExpanded) }

    ListItem(
        modifier = Modifier.clickable { setExpanded(!isExpanded) },
        headlineText = { Text(text = category.name) },
        trailingContent = { Text(text = "${categoryItems.sumOf { it.count }}/${categoryItems.sumOf { it.totalCount }}") },
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
    if (isExpanded) {
        ClothingItemList(categoryItems = categoryItems, clothingViewModel = clothingViewModel, isEditing = isEditing)
    }
}
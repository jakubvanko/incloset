package com.jakubvanko.incloset.presentation.overview.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jakubvanko.incloset.domain.model.ClothingCategory
import com.jakubvanko.incloset.domain.model.ClothingItem
import com.jakubvanko.incloset.presentation.ClothingViewModel
import kotlin.reflect.KProperty1

fun getSumForCategory(
    category: ClothingCategory,
    clothingViewModel: ClothingViewModel,
    property: KProperty1<ClothingItem, Int>
): Int {
    return clothingViewModel.clothingItems.filter { it.category == category }
        .sumOf { property.get(it) }
}

@Composable
fun CategoryCountText(category: ClothingCategory, clothingViewModel: ClothingViewModel) {
    Text(
        text = "${
            clothingViewModel.clothingItems.filter { it.category == category }
                .sumOf { it.count }
        }/${
            clothingViewModel.clothingItems.filter { it.category == category }
                .sumOf { it.totalCount }
        }"
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryList(
    clothingViewModel: ClothingViewModel,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(clothingViewModel.clothingCategories) {
            ListItem(
                modifier = Modifier.clickable { clothingViewModel.flipCategoryViewExpanded(category = it) },
                headlineText = { Text(text = it.name) },
                trailingContent = {
                    CategoryCountText(clothingViewModel = clothingViewModel, category = it)
                },
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
            if (clothingViewModel.categoryViewExpanded.getOrDefault(it.id, false)) {
                ClothingItemSubList(
                    category = it,
                    clothingViewModel = clothingViewModel
                )
            }
        }
    }
}
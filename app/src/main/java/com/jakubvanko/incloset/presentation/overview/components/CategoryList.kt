package com.jakubvanko.incloset.presentation.overview.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jakubvanko.incloset.domain.model.ClosetStatus
import com.jakubvanko.incloset.domain.model.ClothingCategory
import com.jakubvanko.incloset.presentation.ClothingViewModel

@Composable
private fun CategoryCountText(category: ClothingCategory, clothingViewModel: ClothingViewModel) {
    Text(text = "${
        clothingViewModel.clothingItems.filter { it.categoryId == category.id }.sumOf { it.count }
    }/${
        clothingViewModel.clothingItems.filter { it.categoryId == category.id }.sumOf { it.totalCount }
    }")
}

private fun getClosetStatusForCategory(
    clothingViewModel: ClothingViewModel,
    category: ClothingCategory
): ClosetStatus {
    val categoryItemCount =
        clothingViewModel.clothingItems.filter { it.categoryId == category.id }.sumOf { it.count }
    return when {
        categoryItemCount > category.minNeededAmount -> ClosetStatus.Ok
        categoryItemCount == category.minNeededAmount -> ClosetStatus.Warning
        else -> ClosetStatus.Empty
    }
}

@Composable
private fun TrailingContent(clothingViewModel: ClothingViewModel, category: ClothingCategory) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CategoryCountText(category = category, clothingViewModel = clothingViewModel)
        Spacer(modifier = Modifier.width(8.dp))
        ClosetStatusIcon(
            closetStatus = getClosetStatusForCategory(
                clothingViewModel = clothingViewModel,
                category = category
            )
        )
    }
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
                    TrailingContent(clothingViewModel = clothingViewModel, category = it)
                },
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
            if (clothingViewModel.categoryViewExpanded.getOrDefault(it.id, false)) {
                ClothingListForCategory(
                    category = it, clothingViewModel = clothingViewModel
                )
            }
        }
    }
}
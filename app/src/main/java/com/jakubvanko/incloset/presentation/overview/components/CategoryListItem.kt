package com.jakubvanko.incloset.presentation.overview.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.jakubvanko.incloset.domain.model.ClothingCategory
import androidx.compose.material3.ListItemDefaults.colors;

class Colors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryListItem(category: ClothingCategory, shouldStartExpanded: Boolean) {
    var isExpanded by remember { mutableStateOf(shouldStartExpanded) }

    ListItem(
        modifier = Modifier.clickable { isExpanded = !isExpanded },
        headlineText = { Text(text = category.name) },
        trailingContent = { Text(text = "${category.items.sumOf { it.count }}/${category.items.sumOf { it.totalCount }}") },
        colors = colors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
    if (isExpanded) {
        Column() {
            category.items.forEach {
                ClothingItemListItem(item = it)
                Divider()
            }
        }
    }
}
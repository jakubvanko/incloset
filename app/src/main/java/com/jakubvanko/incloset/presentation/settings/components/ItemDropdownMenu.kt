package com.jakubvanko.incloset.presentation.settings.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.jakubvanko.incloset.domain.model.ClothingCategory
import com.jakubvanko.incloset.domain.model.ClothingItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDropdownMenu(
    items: List<ClothingItem>,
    currentItem: ClothingItem,
    setCurrentItem: (ClothingItem) -> Unit,
    categories: List<ClothingCategory>,
) {
    var isExpanded by remember { mutableStateOf(false) }
    Row {
        ExposedDropdownMenuBox(
            modifier = Modifier.fillMaxWidth(),
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded },
        ) {
            TextField(
                // The `menuAnchor` modifier must be passed to the text field for correctness.
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                readOnly = true,
                value = currentItem.name,
                onValueChange = {/* TODO */ },
                label = { Text("Select item") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            val categoryName = categories.find { it.id == item.categoryId }?.name ?: ""
                            Text(text = "[$categoryName] ${item.name}")
                        },
                        onClick = {
                            setCurrentItem(item)
                            isExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
}
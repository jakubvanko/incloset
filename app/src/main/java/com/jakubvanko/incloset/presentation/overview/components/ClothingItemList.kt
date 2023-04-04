package com.jakubvanko.incloset.presentation.overview.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.jakubvanko.incloset.domain.model.ClothingItem
import com.jakubvanko.incloset.presentation.ClothingViewModel

@Composable
fun ClothingItemList(
    categoryItems: List<ClothingItem>,
    clothingViewModel: ClothingViewModel,
    isEditing: Boolean
) {
    val elementStates = remember { mutableMapOf<Int, MutableState<Boolean>>() }
    Column {
        categoryItems.filter { isEditing || it.count > 0 }.forEach {
            val (isItemExpanded, setItemExpanded) = elementStates.getOrPut(it.hashCode()) {
                mutableStateOf(
                    false
                )
            }
            ClothingItemListItem(
                item = it,
                clothingViewModel = clothingViewModel,
                isExpanded = isItemExpanded,
                setExpanded = setItemExpanded
            )
            Divider()
        }
    }
}
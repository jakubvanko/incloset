package com.jakubvanko.incloset.presentation.overview.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jakubvanko.incloset.domain.model.ClothingCategory
import com.jakubvanko.incloset.domain.model.ClothingItem

@Composable
fun CategoryList(
    clothingCategoryList: List<ClothingCategory>,
    clothingItemList: List<ClothingItem>
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(clothingCategoryList) { index, item ->
            CategoryListItem(
                category = item,
                clothingItemList.filter { it.category == item },
                shouldStartExpanded = index == 0
            )
        }
    }
}
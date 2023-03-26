package com.jakubvanko.incloset.presentation.overview.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jakubvanko.incloset.domain.model.ClothingCategory

@Composable
fun CategoryList(clothingCategoryList: List<ClothingCategory>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(clothingCategoryList) { index, item ->
            CategoryListItem(category = item, shouldStartExpanded = index == 0)
        }
    }
}
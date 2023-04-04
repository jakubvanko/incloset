package com.jakubvanko.incloset.presentation.overview

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.jakubvanko.incloset.presentation.ClothingViewModel
import com.jakubvanko.incloset.presentation.overview.components.*

@Composable
fun OverviewScreen(clothingViewModel: ClothingViewModel) {
    val (isEditing, setEditing) = remember { mutableStateOf(false) }

    Column {
        Heading(status = ClosetStatus.Ok, isEditing, setEditing)
        CategoryList(
            clothingCategoryList = clothingViewModel.clothingCategories,
            clothingItemList = clothingViewModel.clothingItems,
            clothingViewModel = clothingViewModel,
            isEditing = isEditing
        )
    }
}
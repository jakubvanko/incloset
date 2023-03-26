package com.jakubvanko.incloset.presentation.overview

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.jakubvanko.incloset.presentation.ClothingViewModel
import com.jakubvanko.incloset.presentation.overview.components.*

@Composable
fun OverviewScreen(clothingViewModel: ClothingViewModel) {
    Column {
        Heading(status = ClosetStatus.Ok)
        CategoryList(clothingCategoryList = clothingViewModel.clothingCategories)
    }
}
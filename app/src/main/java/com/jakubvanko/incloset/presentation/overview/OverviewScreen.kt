package com.jakubvanko.incloset.presentation.overview

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.jakubvanko.incloset.presentation.ClothingViewModel
import com.jakubvanko.incloset.presentation.overview.components.*

@Composable
fun OverviewScreen(clothingViewModel: ClothingViewModel) {
    Column {
        Heading(
            closetStatus = clothingViewModel.currentClosetStatus,
            isEditMode = clothingViewModel.isInEditMode,
            flipEditMode = { clothingViewModel.setEditMode(!clothingViewModel.isInEditMode) }
        )
        CategoryList(
            clothingViewModel = clothingViewModel,
        )
    }
}
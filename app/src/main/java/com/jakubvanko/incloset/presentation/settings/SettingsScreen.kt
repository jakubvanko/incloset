package com.jakubvanko.incloset.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.jakubvanko.incloset.data.repository.SettingsAction
import com.jakubvanko.incloset.presentation.ClothingViewModel
import com.jakubvanko.incloset.presentation.settings.components.*

@Composable
fun SettingsScreen(clothingViewModel: ClothingViewModel, goBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        when (clothingViewModel.currentSettingsAction) {
            SettingsAction.EditCategory -> EditCategory(clothingViewModel = clothingViewModel)
            SettingsAction.EditItem -> EditItem(clothingViewModel = clothingViewModel)
            SettingsAction.CreateCategory -> CreateCategory(clothingViewModel = clothingViewModel, goBack = goBack)
            SettingsAction.CreateItem -> CreateItem(clothingViewModel = clothingViewModel, goBack = goBack)
        }
    }
}
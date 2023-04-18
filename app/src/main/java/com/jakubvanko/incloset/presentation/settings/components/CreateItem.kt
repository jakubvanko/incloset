package com.jakubvanko.incloset.presentation.settings.components

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import com.jakubvanko.incloset.presentation.ClothingViewModel

@Composable
fun CreateItem(clothingViewModel: ClothingViewModel) {
    var name by remember { mutableStateOf("") }
    var count by remember { mutableStateOf("") }
    var totalCount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }



    Text(text = "Create item")
}
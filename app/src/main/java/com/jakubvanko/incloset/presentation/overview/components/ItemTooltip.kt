package com.jakubvanko.incloset.presentation.overview.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jakubvanko.incloset.domain.model.ClothingItem
import com.jakubvanko.incloset.presentation.ClothingViewModel

@Composable
private fun WhateverTextField() {
    BasicTextField(
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .padding(0.dp, 0.dp, 3.dp, 0.dp),
        value = "item.count.toString()",
        onValueChange = {},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.End,
            fontSize = 16.sp
        )
    )
}

@Composable
fun ItemTooltip(item: ClothingItem, clothingViewModel: ClothingViewModel) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        IconButton(onClick = { clothingViewModel.decreaseItemCount(item) }) {
            Icon(
                Icons.Outlined.KeyboardArrowDown,
                tint = Color.hsl(0f, 0.82f, 0.36f),
                contentDescription = "Decrease item count"
            )
        }
        IconButton(onClick = { clothingViewModel.increaseItemCount(item) }) {
            Icon(
                Icons.Outlined.KeyboardArrowUp,
                tint = Color.hsl(138f, 0.83f, 0.27f),
                contentDescription = "Increase item count"
            )
        }
    }
}
package com.jakubvanko.incloset.presentation.overview.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jakubvanko.incloset.domain.model.ClothingItem
import com.jakubvanko.incloset.presentation.ClothingViewModel

@Composable
fun Image() {
    AsyncImage(
        model = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1887&q=80.jkpg",
        contentDescription = "Translated description of what the image contains",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(width = 110.dp, height = 70.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClothingItemListItem(
    item: ClothingItem,
    clothingViewModel: ClothingViewModel
) {
    ListItem(
        modifier = Modifier.clickable { clothingViewModel.flipItemViewExpanded(item) },
        headlineText = { Text(text = item.name) },
        supportingText = {
            if (item.description != null) {
                Text(item.description)
            }
        },
        trailingContent = { Text(text = item.count.toString()) },
        tonalElevation = if (item.count == 0) 10.dp else 0.dp
    )
    if (clothingViewModel.itemViewExpanded.getOrDefault(item.id, false)) {
        ClothingItemListItemTooltip(item = item, clothingViewModel = clothingViewModel)
    }
}
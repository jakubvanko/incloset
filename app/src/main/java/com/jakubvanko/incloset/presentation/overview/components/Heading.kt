package com.jakubvanko.incloset.presentation.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

enum class ClosetStatus {
    Ok,
    Warning,
    Empty
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Heading(status: ClosetStatus) {
    ListItem(headlineText = {
        Text(text = "My Closet", style = MaterialTheme.typography.titleLarge)
    }, trailingContent = {
        Text(text = status.name)
    },
        modifier = Modifier.shadow(5.dp)
    )
}
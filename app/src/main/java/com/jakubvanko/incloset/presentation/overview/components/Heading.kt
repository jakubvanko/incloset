package com.jakubvanko.incloset.presentation.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

enum class ClosetStatus {
    Ok,
    Warning,
    Empty
}

@Composable
fun Heading(status: ClosetStatus) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        Text(text = "My Closet")
        Text(text = status.name)
    }
}
package com.jakubvanko.incloset.presentation.overview.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.jakubvanko.incloset.domain.model.ClosetStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Heading(closetStatus: ClosetStatus, isEditMode: Boolean, flipEditMode: () -> Unit) {
    ListItem(
        headlineText = {
            Text(text = "My Closet", style = MaterialTheme.typography.titleLarge)
        },
        leadingContent = {
            IconButton(onClick = flipEditMode) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "Enter edit mode",
                    tint = if (isEditMode) Color.Green else Color.Red
                )
            }
        },
        trailingContent = {
            ClosetStatusIcon(closetStatus = closetStatus)
        },
    )
}
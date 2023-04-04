package com.jakubvanko.incloset.presentation.overview.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

enum class ClosetStatus {
    Ok,
    Warning,
    Empty
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Heading(status: ClosetStatus, isEditing: Boolean, setEditing: (Boolean) -> Unit) {
    ListItem(headlineText = {
        Text(text = "My Closet", style = MaterialTheme.typography.titleLarge)
    }, leadingContent = {
        IconButton(onClick = { setEditing(!isEditing) }) {
            Icon(
                Icons.Outlined.Edit,
                contentDescription = "Enter edit version"
            )
        }
    },
        trailingContent = {
            Text(text = status.name)
        },
        modifier = Modifier.shadow(5.dp)
    )
}
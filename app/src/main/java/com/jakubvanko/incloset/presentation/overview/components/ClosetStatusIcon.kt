package com.jakubvanko.incloset.presentation.overview.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.jakubvanko.incloset.domain.model.ClosetStatus

private fun getIconToDisplay(closetStatus: ClosetStatus): ImageVector {
    return when (closetStatus) {
        ClosetStatus.Empty -> Icons.Outlined.Close
        ClosetStatus.Warning -> Icons.Outlined.Warning
        ClosetStatus.Ok -> Icons.Outlined.Check
    }
}

private fun getTintForStatusIcon(closetStatus: ClosetStatus): Color {
    return when (closetStatus) {
        ClosetStatus.Empty -> Color.Red
        ClosetStatus.Warning -> Color.Yellow
        ClosetStatus.Ok -> Color.Green
    }
}

@Composable
fun ClosetStatusIcon(closetStatus: ClosetStatus) {
    Icon(
        imageVector = getIconToDisplay(closetStatus),
        contentDescription = "Closet status",
        tint = getTintForStatusIcon(closetStatus)
    )
}
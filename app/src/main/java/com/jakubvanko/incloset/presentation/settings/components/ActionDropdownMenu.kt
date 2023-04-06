package com.jakubvanko.incloset.presentation.settings.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.jakubvanko.incloset.data.repository.SettingsAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionDropdownMenu(currentAction: SettingsAction, setCurrentAction: (SettingsAction) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    Row {
        ExposedDropdownMenuBox(
            modifier = Modifier.fillMaxWidth(),
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded },
        ) {
            TextField(
                // The `menuAnchor` modifier must be passed to the text field for correctness.
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                readOnly = true,
                value = currentAction.name,
                onValueChange = {/* TODO */ },
                label = { Text("Select action") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
            ) {
                SettingsAction.values().forEach {
                    DropdownMenuItem(
                        text = { Text(text = it.name) },
                        onClick = {
                            setCurrentAction(it)
                            isExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
}
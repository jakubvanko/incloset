package com.jakubvanko.incloset.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material3.ListItem
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType.Companion.Number
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jakubvanko.incloset.domain.model.ClothingCategory
import com.jakubvanko.incloset.domain.model.ClothingItem

@Preview
@Composable
fun Preview1() {
    ManageScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageScreen() {
    val categories = listOf(
        ClothingCategory(
            "T-shirts", 1, "T-shirts with a short sleeve", mutableListOf(
                ClothingItem("Black T-shirt", 1, 1, "With a cat", null),
                ClothingItem("Red T-shirt with stars", 1, 1, null, null),
                ClothingItem("Dark green T-shirt", 1, 1, null, null),
                ClothingItem("Basic T-shirt", 3, 4, null, null),
                ClothingItem("Special T-shirt", 0, 2, null, null)
            )
        ),
        ClothingCategory(
            "Trousers", 1, "Basic jeans", mutableListOf(
                ClothingItem("Whitewashed jeans", 1, 1, "Informal", null),
                ClothingItem("Black jeans", 2, 3, "Good for everyday usage", null)
            )
        ),
        ClothingCategory(
            "Underwear", 2, "Boxer briefs", mutableListOf(
                ClothingItem("Underwear", 8, 12, "Boxers and briefs", null)
            )
        )
    )

    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(categories[0]) }

    Column(
        modifier = Modifier
            .fillMaxSize()
        //.padding(20.dp, 0.dp)
    ) {
        Row {
            ExposedDropdownMenuBox(
                modifier = Modifier.fillMaxWidth(),
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
            ) {
                TextField(
                    // The `menuAnchor` modifier must be passed to the text field for correctness.
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    readOnly = true,
                    value = selectedCategory.name,
                    onValueChange = {},
                    label = { Text("Select category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.name) },
                            onClick = {
                                selectedCategory = category
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }
        }
        selectedCategory.items.forEach { item ->
            ListItem(
                headlineText = { Text(item.name) },
                supportingText = {
                    if (item.description != null) {
                        Text(item.description)
                    }
                },
                overlineText = {
                    Row {
                        BasicTextField(
                            modifier = Modifier
                                .width(IntrinsicSize.Min)
                                .padding(0.dp, 0.dp, 3.dp, 0.dp),
                            value = item.count.toString(),
                            onValueChange = {},
                            keyboardOptions = KeyboardOptions(keyboardType = Number),
                            textStyle = LocalTextStyle.current.copy(
                                textAlign = TextAlign.End,
                                fontSize = 16.sp
                            )
                        )
                        Text("/", fontSize = 16.sp)
                        BasicTextField(
                            modifier =
                            Modifier
                                .width(IntrinsicSize.Min)
                                .padding(3.dp, 0.dp, 0.dp, 0.dp),
                            value = item.totalCount.toString(),
                            onValueChange = {},
                            keyboardOptions = KeyboardOptions(keyboardType = Number),
                            textStyle = LocalTextStyle.current.copy(
                                textAlign = TextAlign.Left,
                                fontSize = 16.sp
                            )
                        )
                    }
                },
                trailingContent = {
                    Column() {
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                Icons.Outlined.KeyboardArrowUp,
                                tint = Color.hsl(138f, 0.83f, 0.27f),
                                contentDescription = "Localized description"
                            )
                        }
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                Icons.Outlined.KeyboardArrowDown,
                                tint = Color.hsl(0f, 0.82f, 0.36f),
                                contentDescription = "Localized description"
                            )
                        }
                    }
                }
            )
            Divider()
        }
    }
}
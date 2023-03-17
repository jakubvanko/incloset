package com.jakubvanko.incloset.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.KeyboardType.Companion.Number
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jakubvanko.incloset.data.ClothingCategory
import com.jakubvanko.incloset.data.ClothingPiece

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
                ClothingPiece("Black T-shirt", 1, 1, "With a cat", null),
                ClothingPiece("Red T-shirt with stars", 1, 1, null, null),
                ClothingPiece("Dark green T-shirt", 1, 1, null, null),
                ClothingPiece("Basic T-shirt", 3, 4, null, null),
                ClothingPiece("Special T-shirt", 0, 2, null, null)
            )
        ),
        ClothingCategory(
            "Trousers", 1, "Basic jeans", mutableListOf(
                ClothingPiece("Whitewashed jeans", 1, 1, "Informal", null),
                ClothingPiece("Black jeans", 2, 3, "Good for everyday usage", null)
            )
        ),
        ClothingCategory(
            "Underwear", 2, "Boxer briefs", mutableListOf(
                ClothingPiece("Underwear", 8, 12, "Boxers and briefs", null)
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
                leadingContent = {
                    AsyncImage(
                        model = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1887&q=80.jkpg",
                        contentDescription = "Translated description of what the image contains",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
                            .align(Alignment.CenterHorizontally)
                    )
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
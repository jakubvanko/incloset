package com.jakubvanko.incloset.presentation.settings.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import com.jakubvanko.incloset.presentation.ClothingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateItem(clothingViewModel: ClothingViewModel) {
    var name by remember { mutableStateOf("") }
    var isNameError by remember { mutableStateOf(false) }

    var description by remember { mutableStateOf("") }

    var count by remember { mutableStateOf("1") }
    var isCountError by remember { mutableStateOf(false) }

    var totalCount by remember { mutableStateOf("1") }
    var isTotalCountError by remember { mutableStateOf(false) }

    var category by remember { mutableStateOf(clothingViewModel.clothingCategories.getOrNull(0)) }

    var isSuccess by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(24.dp, 32.dp)
    ) {
        if (category == null) {
            Text("No categories exist, create some first.", color = Color.Red)
            return@Column
        }
        CategoryDropdownMenu(
            categories = clothingViewModel.clothingCategories,
            currentCategory = category!!,
            setCurrentCategory = { category = it })
        Spacer(modifier = Modifier.height(24.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = name,
            onValueChange = {
                name = it.capitalize(Locale.current)
                isNameError = false
            },
            label = { Text("Name") },
            isError = isNameError,
            supportingText = {
                if (isNameError) {
                    Text("Name must not be empty", color = Color.Red)
                }
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") }
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = count,
            onValueChange = {
                if (it == "" || it.toIntOrNull() != null) {
                    count = it
                    isCountError = false
                }
            },
            label = { Text("Actual amount in closet") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = isCountError,
            supportingText = {
                if (isCountError) {
                    Text("Minimum needed amount must be a number", color = Color.Red)
                }
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = totalCount,
            onValueChange = {
                if (it == "" || it.toIntOrNull() != null) {
                    totalCount = it
                    isTotalCountError = false
                }
            },
            label = { Text("Total amount you own") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = isTotalCountError,
            supportingText = {
                if (isTotalCountError) {
                    Text("Minimum needed amount must be a number", color = Color.Red)
                }
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            if (name == "") {
                isNameError = true
            }
            if (count == "") {
                isCountError = true
            }
            if (totalCount == "") {
                isTotalCountError = true
            }
            if (name != "" && count != "" && totalCount != "") {
                clothingViewModel.createItem(
                    name,
                    count.toInt(),
                    if (totalCount.toInt() < count.toInt()) count.toInt() else totalCount.toInt(),
                    category!!,
                    if (description == "") null else description
                )
                name = ""
                description = ""
                count = "1"
                totalCount = "1"
                isSuccess = true
            }
        }) {
            Text("Create new item")
        }
        if (isSuccess && !isNameError && !isCountError && !isTotalCountError) {
            Text("Category created successfully", color = Color.Green)
        }
    }
}
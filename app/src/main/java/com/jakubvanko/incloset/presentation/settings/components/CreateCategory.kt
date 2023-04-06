package com.jakubvanko.incloset.presentation.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
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
import com.jakubvanko.incloset.presentation.ClothingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCategory(clothingViewModel: ClothingViewModel) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var minNeededAmount by remember { mutableStateOf("0") }
    var isNameError by remember { mutableStateOf(false) }
    var isMinNeededAmountError by remember { mutableStateOf(false) }
    var isSuccess by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = name,
            onValueChange = {
                name = it.capitalize(Locale.current)
                isNameError = false
            },
            label = { Text("Name") })
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") })
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = minNeededAmount,
            onValueChange = {
                if (it == "" || it.toIntOrNull() != null) {
                    minNeededAmount = it
                    isMinNeededAmountError = false
                }
            },
            label = { Text("Minimum needed amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        Button(onClick = {
            if (minNeededAmount == "") {
                isMinNeededAmountError = true
            } else if (name == "") {
                isNameError = true
            } else {
                clothingViewModel.createCategory(name, minNeededAmount.toInt(), description)
                name = ""
                description = ""
                minNeededAmount = "0"
                isSuccess = true
            }
        }) {
            Text("Create new category")
        }
        if (isNameError) {
            Text("Name cannot be empty", color = Color.Red)
        }
        if (isMinNeededAmountError) {
            Text("Minimum needed amount must be a number", color = Color.Red)
        }
        if (isSuccess && !isNameError && !isMinNeededAmountError) {
            Text("Category created successfully", color = Color.Green)
        }
    }
}
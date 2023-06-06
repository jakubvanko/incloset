package com.jakubvanko.incloset.presentation.settings.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jakubvanko.incloset.presentation.ClothingViewModel
import com.jakubvanko.incloset.presentation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCategory(clothingViewModel: ClothingViewModel, goBack: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var isNameError by remember { mutableStateOf(false) }

    var minNeededAmount by remember { mutableStateOf("1") }
    var isMinNeededAmountError by remember { mutableStateOf(false) }

    var isSuccess by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(24.dp, 32.dp)
    ) {
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
            value = minNeededAmount,
            onValueChange = {
                if (it == "" || it.toIntOrNull() != null) {
                    minNeededAmount = it
                    isMinNeededAmountError = false
                }
            },
            label = { Text("Minimum needed amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = isMinNeededAmountError,
            supportingText = {
                if (isMinNeededAmountError) {
                    Text("Minimum needed amount must be a number", color = Color.Red)
                }
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            if (minNeededAmount == "") {
                isMinNeededAmountError = true
            }
            if (name == "") {
                isNameError = true
            }
            if (name != "" && minNeededAmount != "") {
                clothingViewModel.createCategory(name, minNeededAmount.toInt())
                name = ""
                minNeededAmount = "1"
                isSuccess = true
                goBack()
            }
        }) {
            Text("Create new category")
        }
        if (isSuccess && !isNameError && !isMinNeededAmountError) {
            Text("Category created successfully", color = Color.Green)
        }
    }
}
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
import androidx.navigation.NavHostController
import com.jakubvanko.incloset.presentation.ClothingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCategory(clothingViewModel: ClothingViewModel) {
    var categoryToEdit by remember { mutableStateOf(clothingViewModel.clothingCategories.getOrNull(0)) }

    var name by remember { mutableStateOf(categoryToEdit?.name ?: "No category selected") }
    var isNameError by remember { mutableStateOf(false) }

    var minNeededAmount by remember { mutableStateOf(categoryToEdit?.minNeededAmount?.toString()) }
    var isMinNeededAmountError by remember { mutableStateOf(false) }

    var isSuccessEdit by remember { mutableStateOf(false) }
    var isSuccessDelete by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(24.dp, 32.dp)
    ) {
        if (categoryToEdit == null) {
            Text("No categories exist, create some first.", color = Color.Red)
            return@Column
        }
        CategoryDropdownMenu(
            categories = clothingViewModel.clothingCategories,
            currentCategory = categoryToEdit!!,
            setCurrentCategory = {
                categoryToEdit = it
                name = it.name
                minNeededAmount = it.minNeededAmount.toString()
                isNameError = false
                isMinNeededAmountError = false
                isSuccessEdit = false
                isSuccessDelete = false
            })
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
            value = minNeededAmount!!,
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
        Text("WARNING: Deleting a category also deletes all corresponding items", color = Color.Red)
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(modifier = Modifier
                .fillMaxWidth()
                .weight(1f), onClick = {
                if (minNeededAmount == "") {
                    isMinNeededAmountError = true
                }
                if (name == "") {
                    isNameError = true
                }
                if (name != "" && minNeededAmount != "") {
                    categoryToEdit = clothingViewModel.editCategory(
                        categoryToEdit!!,
                        name,
                        minNeededAmount!!.toInt()
                    )
                    isSuccessEdit = true
                    isSuccessDelete = false
                }
            }) {
                Text("Save changes")
            }
            Spacer(modifier = Modifier.width(24.dp))
            Button(modifier = Modifier
                .fillMaxWidth()
                .weight(1f), onClick = {
                clothingViewModel.deleteCategory(categoryToEdit!!)
                categoryToEdit = clothingViewModel.clothingCategories.getOrNull(0)
                name = categoryToEdit?.name ?: "No category selected"
                minNeededAmount = categoryToEdit?.minNeededAmount?.toString()
                isSuccessDelete = true
                isSuccessEdit = false
            }) {
                Text("Delete category")
            }
        }
        if (isSuccessEdit && !isNameError && !isMinNeededAmountError) {
            Text("Category edited successfully", color = Color.Green)
        }
        if (isSuccessDelete && !isNameError && !isMinNeededAmountError) {
            Text("Category deleted successfully", color = Color.Green)
        }
    }
}
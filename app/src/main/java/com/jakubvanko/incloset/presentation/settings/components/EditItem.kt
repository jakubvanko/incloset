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
fun EditItem(clothingViewModel: ClothingViewModel) {
    var itemToEdit by remember { mutableStateOf(clothingViewModel.clothingItems.getOrNull(0)) }

    var name by remember { mutableStateOf(itemToEdit?.name ?: "No item selected") }
    var isNameError by remember { mutableStateOf(false) }

    var description by remember { mutableStateOf(itemToEdit?.description) }

    var count by remember { mutableStateOf(itemToEdit?.count?.toString()) }
    var isCountError by remember { mutableStateOf(false) }

    var totalCount by remember { mutableStateOf(itemToEdit?.totalCount?.toString()) }
    var isTotalCountError by remember { mutableStateOf(false) }

    var category by remember {
        mutableStateOf(clothingViewModel.clothingCategories.find {
            it.id == itemToEdit?.categoryId
        })
    }

    var isSuccessEdit by remember { mutableStateOf(false) }
    var isSuccessDelete by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(24.dp, 32.dp)
    ) {
        if (itemToEdit == null) {
            Text("No items exist, create some first.", color = Color.Red)
            return@Column
        }
        ItemDropdownMenu(
            items = clothingViewModel.clothingItems,
            currentItem = itemToEdit!!,
            setCurrentItem = {
                itemToEdit = it
                name = it.name
                description = it.description
                count = it.count.toString()
                totalCount = it.totalCount.toString()
                category =
                    clothingViewModel.clothingCategories.find { category -> category.id == it.categoryId }
                isSuccessEdit = false
                isSuccessDelete = false
            },
            categories = clothingViewModel.clothingCategories
        )
        Spacer(modifier = Modifier.height(24.dp))


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
            value = description.orEmpty(),
            onValueChange = { description = it },
            label = { Text("Description") }
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = count!!,
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
            value = totalCount!!,
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
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(modifier = Modifier
                .fillMaxWidth()
                .weight(1f), onClick = {
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
                    itemToEdit = clothingViewModel.editItem(
                        itemToEdit!!,
                        name,
                        count?.toIntOrNull() ?: 0,
                        if ((totalCount?.toIntOrNull() ?: 0) < (count?.toIntOrNull()
                                ?: 0)
                        ) count?.toIntOrNull() ?: 0 else totalCount?.toIntOrNull() ?: 0,
                        category!!,
                        if (description == "") null else description
                    )
                    count = itemToEdit?.count?.toString()
                    totalCount = itemToEdit?.totalCount?.toString()
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
                clothingViewModel.deleteItem(itemToEdit!!)
                itemToEdit = clothingViewModel.clothingItems.getOrNull(0)
                name = itemToEdit?.name ?: "No item selected"
                description = itemToEdit?.description.orEmpty()
                count = itemToEdit?.count?.toString()
                totalCount = itemToEdit?.totalCount?.toString()
                category =
                    clothingViewModel.clothingCategories.find { category -> category.id == itemToEdit?.categoryId }
                isSuccessDelete = true
                isSuccessEdit = false
            }) {
                Text("Delete item")
            }
        }
        if (isSuccessEdit && !isNameError && !isCountError && !isTotalCountError) {
            Text("Item edited successfully", color = Color.Green)
        }
        if (isSuccessDelete && !isNameError && !isCountError && !isTotalCountError) {
            Text("Item deleted successfully", color = Color.Green)
        }
    }
}
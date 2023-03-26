package com.jakubvanko.incloset.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun Preview3() {
    SettingsScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, 0.dp)
    ) {
        Card() {
            Column() {
                Text("Create new category")
                TextField(value = "", onValueChange = {}, label = { Text("Name") })
                TextField(value = "", onValueChange = {}, label = { Text("Description") })
                TextField(value = "", onValueChange = {}, label = { Text("Minimum needed amount") })
                Button(onClick = { /*TODO*/ }) {
                    Text("Create")
                }
            }
        }
        Card() {
            Column() {
                Text("Create new item")
                TextField(value = "", onValueChange = {}, label = { Text("Name") })
                TextField(value = "", onValueChange = {}, label = { Text("Description") })
                TextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Actual amount in closet") })
                TextField(value = "", onValueChange = {}, label = { Text("Total possible amount") })
                Button(onClick = { /*TODO*/ }) {
                    Text("Create")
                }
            }
        }
    }
}
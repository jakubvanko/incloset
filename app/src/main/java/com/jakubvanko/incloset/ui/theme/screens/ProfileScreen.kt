package com.jakubvanko.incloset.ui.theme.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoCard(keyText: String?, valueText: String) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(0.dp, 5.dp)
    ) {
        Column(Modifier.padding(15.dp, 10.dp)) {
            if (keyText != null) {
                Text(keyText, fontWeight = FontWeight.Bold)
            }
            Text(valueText)
        }
    }
}

//´TODO: Something with image. Button. Take arguments.
@Preview(showBackground = true)
@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, 0.dp)
    ) {
        AsyncImage(
            model = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1887&q=80.jkpg",
            contentDescription = "Translated description of what the image contains",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(0.dp, 30.dp, 0.dp, 20.dp)
                .size(160.dp)
                .clip(CircleShape)                       // clip to the circle shape
                .border(2.dp, Color.Black, CircleShape)
                .align(Alignment.CenterHorizontally)
        )

        InfoCard("Name", "Jakub Vanko")
        InfoCard("Email", "kubko.vanko@gmail.com")
        InfoCard("Registered on", "Mar 15, 2023")
        InfoCard("Free/Premium", "Running free version")
        OutlinedButton(
            modifier = Modifier
                .align(Alignment.End)
                .fillMaxWidth(0.35f),
            onClick = { /*TODO*/ }) {
            Text("Log out")
        }
    }
}
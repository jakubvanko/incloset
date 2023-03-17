package com.jakubvanko.incloset.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jakubvanko.incloset.data.ClothingCategory
import com.jakubvanko.incloset.data.ClothingPiece

@Preview(showBackground = true)
@Composable
fun Preview() {
    OverviewScreen()
}

@Composable
fun ClosetHeading() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text("My Closet")
        Icon(Icons.Rounded.Check, contentDescription = "Status icon")
    }
}

@Composable
fun ItemRow(item: ClothingPiece) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("- ${item.name}")
        Row {
            if (item.picture != null) {
                // TODO: photo
                Icon(Icons.Rounded.Share, contentDescription = "PhotoIcon")
                Spacer(modifier = Modifier.size(5.dp))
            }
            Text("${item.count}/${item.totalCount}")
        }
    }
}

// TODO: Generalize
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCard(category: ClothingCategory) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(0.dp, 5.dp)
    ) {
        Column(Modifier.padding(15.dp, 10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(category.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("${category.items.sumOf { item -> item.count }}/${category.items.sumOf { item -> item.totalCount }}")
            }
            Spacer(modifier = Modifier.size(20.dp))
            category.items.forEach { item ->
                ItemRow(item)
            }
        }
    }
}

@Composable
fun OverviewScreen() {
    val categories = listOf(
        ClothingCategory(
            "T-shirts", 1, "T-shirts with a short sleeve", mutableListOf(
                ClothingPiece("Black T-shirt", 1, 1, "With a cat", null),
                ClothingPiece("Red T-shirt with stars", 1, 1, null, null),
                ClothingPiece("Dark green T-shirt", 1, 1, null, null),
                ClothingPiece("Basic T-shirt", 3, 4, null, null)
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, 0.dp)
    ) {
        ClosetHeading()
        categories.forEach { category ->
            CategoryCard(category)
        }
    }
}
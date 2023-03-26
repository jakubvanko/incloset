package com.jakubvanko.incloset.presentation.overview

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jakubvanko.incloset.domain.model.ClothingCategory
import com.jakubvanko.incloset.domain.model.ClothingItem
import com.jakubvanko.incloset.presentation.overview.components.*

@Preview
@Composable
fun OverviewScreen() {
    Column {
        Heading(status = ClosetStatus.Ok)
        CategoryList(clothingCategoryList = getCategoriesMock())
    }
}


fun getCategoriesMock(): List<ClothingCategory> {
    return listOf(
        ClothingCategory(
            "T-shirts", 1, "T-shirts with a short sleeve", mutableListOf(
                ClothingItem("Black T-shirt", 1, 1, "With a cat", null),
                ClothingItem("Red T-shirt with stars", 1, 1, null, null),
                ClothingItem("Dark green T-shirt", 1, 1, null, null),
                ClothingItem("Basic T-shirt", 3, 4, null, null),
                ClothingItem("Basic T-shirt", 0, 2, null, null)
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
}
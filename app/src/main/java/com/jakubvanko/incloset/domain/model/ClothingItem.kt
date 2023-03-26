package com.jakubvanko.incloset.domain.model

data class ClothingItem(
    val id: String,
    val name: String,
    val count: Int,
    val totalCount: Int,
    val category: ClothingCategory,
    val description: String?,
    val picture: String?,
)
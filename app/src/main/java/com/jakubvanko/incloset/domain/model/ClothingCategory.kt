package com.jakubvanko.incloset.domain.model

data class ClothingCategory(
    val name: String,
    val minNeededAmount: Int,
    val description: String?,
    val items: MutableList<ClothingItem>
)
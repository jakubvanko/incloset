package com.jakubvanko.incloset.domain.model

data class ClothingCategory(
    val id: String,
    val name: String,
    val minNeededAmount: Int,
)
package com.jakubvanko.incloset.domain.model

data class ClothingItem(
    val name: String,
    val count: Int,
    val totalCount: Int,
    val description: String?,
    val picture: String?,
)
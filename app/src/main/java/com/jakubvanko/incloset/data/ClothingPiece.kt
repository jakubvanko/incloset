package com.jakubvanko.incloset.data

data class ClothingPiece(
    val name: String,
    val count: Int,
    val totalCount: Int,
    val description: String?,
    val picture: String?,
) {
}
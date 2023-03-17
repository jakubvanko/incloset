package com.jakubvanko.incloset.data

data class ClothingCategory(
    val name: String,
    val minNeededAmount: Int,
    val description: String?,
    val items: MutableList<ClothingPiece>
) {
    fun addClothingPiece(clothingPiece: ClothingPiece) {
        items.add(clothingPiece)
    }
}
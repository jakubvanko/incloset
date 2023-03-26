package com.jakubvanko.incloset.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.jakubvanko.incloset.data.repository.ClothingRepository
import com.jakubvanko.incloset.domain.model.ClothingCategory
import com.jakubvanko.incloset.domain.model.ClothingItem

class ClothingViewModel : ViewModel() {
    private val clothingRepository = ClothingRepository();
    private val _clothingCategories = mutableStateListOf<ClothingCategory>()
    val clothingCategories: List<ClothingCategory> = _clothingCategories

    init {
        _clothingCategories.addAll(clothingRepository.getClothingCategories());
    }

    fun addClothingCategory(
        name: String,
        minNeededAmount: Int,
        description: String?,
        items: MutableList<ClothingItem>
    ) {
        _clothingCategories.add(ClothingCategory(name, minNeededAmount, description, items))
    }
}
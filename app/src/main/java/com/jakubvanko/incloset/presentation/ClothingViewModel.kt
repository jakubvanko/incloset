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
    private val _clothingItems = mutableStateListOf<ClothingItem>()
    val clothingItems: List<ClothingItem> = _clothingItems

    init {
        val repositoryResult = clothingRepository.getClothingCategories()
        _clothingCategories.addAll(repositoryResult.second);
        _clothingItems.addAll(repositoryResult.first)
    }

    fun setItemCount(item: ClothingItem, newCount: Int) {
        var countToSet = newCount
        if (countToSet < 0) {
            countToSet = 0
        }
        if (countToSet > item.totalCount) {
            countToSet = item.totalCount
        }
        val index = _clothingItems.indexOf(item)
        _clothingItems[index] = _clothingItems[index].copy(count = countToSet)
    }

    fun decreaseItemCount(item: ClothingItem) {
        setItemCount(item, item.count - 1)
    }

    fun increaseItemCount(item: ClothingItem) {
        setItemCount(item, item.count + 1)
    }
}
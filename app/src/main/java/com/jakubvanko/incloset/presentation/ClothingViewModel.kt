package com.jakubvanko.incloset.presentation

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.jakubvanko.incloset.data.repository.ClothingRepository
import com.jakubvanko.incloset.domain.model.ClosetStatus
import com.jakubvanko.incloset.domain.model.ClothingCategory
import com.jakubvanko.incloset.domain.model.ClothingItem

class ClothingViewModel : ViewModel() {
    private val clothingRepository = ClothingRepository();
    private val _clothingCategories = mutableStateListOf<ClothingCategory>()
    val clothingCategories: List<ClothingCategory> = _clothingCategories
    private val _clothingItems = mutableStateListOf<ClothingItem>()
    val clothingItems: List<ClothingItem> = _clothingItems


    var isInEditMode by mutableStateOf(false)
        private set
    var currentClosetStatus by mutableStateOf(ClosetStatus.Ok)
        private set
    private val _itemViewExpanded = mutableStateMapOf<String, Boolean>()
    val itemViewExpanded: Map<String, Boolean> = _itemViewExpanded
    private val _categoryViewExpanded = mutableStateMapOf<String, Boolean>()
    val categoryViewExpanded: Map<String, Boolean> = _categoryViewExpanded

    init {
        val repositoryResult = clothingRepository.getClothingCategories()
        _clothingCategories.addAll(repositoryResult.second);
        _clothingCategories.sortBy { it.name }
        _categoryViewExpanded[_clothingCategories.first().id] = true
        _clothingItems.addAll(repositoryResult.first)
        _clothingItems.sortBy { it.name }
    }

    fun setItemCount(item: ClothingItem, newCount: Int) {
        var countToSet = newCount
        if (countToSet < 0) {
            countToSet = 0
        }
        if (countToSet > item.totalCount) {
            countToSet = item.totalCount
        }
        if (countToSet == 0) {
            _itemViewExpanded[item.id] = false
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

    fun flipItemViewExpanded(item: ClothingItem) {
        _itemViewExpanded[item.id] = !_itemViewExpanded.getOrDefault(item.id, false)
    }

    fun flipCategoryViewExpanded(category: ClothingCategory) {
        _categoryViewExpanded[category.id] = !_categoryViewExpanded.getOrDefault(category.id, false)
    }

    fun setEditMode(newMode: Boolean) {
        isInEditMode = newMode
        if (!newMode) {
            _itemViewExpanded.clear()
        }
    }
}
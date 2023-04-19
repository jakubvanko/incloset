package com.jakubvanko.incloset.presentation

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.jakubvanko.incloset.data.repository.ClothingRepository
import com.jakubvanko.incloset.data.repository.SettingsAction
import com.jakubvanko.incloset.domain.model.ClosetStatus
import com.jakubvanko.incloset.domain.model.ClothingCategory
import com.jakubvanko.incloset.domain.model.ClothingItem

class ClothingViewModel : ViewModel() {
    private val clothingRepository = ClothingRepository(this)
    private val _clothingCategories = mutableStateListOf<ClothingCategory>()
    val clothingCategories: List<ClothingCategory> = _clothingCategories
    private val _clothingItems = mutableStateListOf<ClothingItem>()
    val clothingItems: List<ClothingItem> = _clothingItems


    var isInEditMode by mutableStateOf(false)
        private set
    var currentClosetStatus by mutableStateOf(ClosetStatus.Ok)
        private set
    private val _itemViewExpanded = mutableStateMapOf<String, Boolean>()
    val itemTooltipVisible: Map<String, Boolean> = _itemViewExpanded
    private val _categoryViewExpanded = mutableStateMapOf<String, Boolean>()
    val categoryViewExpanded: Map<String, Boolean> = _categoryViewExpanded
    var currentSettingsAction by mutableStateOf(SettingsAction.CreateCategory)

    init {
        clothingRepository.getClothingCategories()
        clothingRepository.getClothingItems()
    }

    fun setClothingItems(clothingItems: List<ClothingItem>) {
        _clothingItems.clear()
        _clothingItems.addAll(clothingItems)
        _clothingItems.sortBy { it.name }
        updateClosetStatus()
    }

    fun setClothingCategories(clothingCategories: List<ClothingCategory>) {
        _clothingCategories.clear()
        _clothingCategories.addAll(clothingCategories)
        _clothingCategories.sortBy { it.name }
        if (_clothingCategories.isNotEmpty()) {
            _categoryViewExpanded[_clothingCategories.first().id] = true
        }
        updateClosetStatus()
    }

    private fun updateClosetStatus() {
        val statusPerCategory = clothingCategories.map {
            val categoryItems = _clothingItems.filter { item -> item.categoryId == it.id }
            val categoryItemCount = categoryItems.sumOf { item -> item.count }
            if (categoryItemCount > it.minNeededAmount) {
                ClosetStatus.Ok
            } else if (categoryItemCount == it.minNeededAmount) {
                ClosetStatus.Warning
            } else {
                ClosetStatus.Empty
            }
        }
        if (statusPerCategory.contains(ClosetStatus.Empty)) {
            currentClosetStatus = ClosetStatus.Empty
        } else if (statusPerCategory.contains(ClosetStatus.Warning)) {
            currentClosetStatus = ClosetStatus.Warning
        } else {
            currentClosetStatus = ClosetStatus.Ok
        }
    }

    private fun setItemCount(item: ClothingItem, newCount: Int) {
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
        clothingRepository.saveClothingItem(_clothingItems[index])
        updateClosetStatus()
    }

    fun createCategory(name: String, minNeededAmount: Int) {
        val newCategory = ClothingCategory(
            id = clothingRepository.generateId(),
            name = name,
            minNeededAmount = minNeededAmount,
            userId = null
        )
        _clothingCategories.add(newCategory)
        _clothingCategories.sortBy { it.name }
        clothingRepository.saveClothingCategory(newCategory)
        updateClosetStatus()
    }

    fun editCategory(
        categoryToEdit: ClothingCategory,
        name: String,
        minNeededAmount: Int
    ): ClothingCategory {
        val index = _clothingCategories.indexOf(categoryToEdit)
        _clothingCategories[index] = _clothingCategories[index].copy(
            name = name,
            minNeededAmount = minNeededAmount
        )
        _clothingCategories.sortBy { it.name }
        updateClosetStatus()
        clothingRepository.saveClothingCategory(_clothingCategories[index])
        return _clothingCategories[index]
    }

    fun deleteCategory(categoryToDelete: ClothingCategory) {
        _clothingCategories.remove(categoryToDelete)
        _clothingItems.filter { it.categoryId == categoryToDelete.id }.forEach {
            clothingRepository.deleteClothingItem(it)
        }
        _clothingItems.removeAll { it.categoryId == categoryToDelete.id }
        clothingRepository.deleteClothingCategory(categoryToDelete)
        updateClosetStatus()
    }

    fun createItem(
        name: String,
        count: Int,
        totalCount: Int,
        category: ClothingCategory,
        description: String?
    ) {
        val newItem = ClothingItem(
            id = clothingRepository.generateId(),
            name = name,
            count = count,
            totalCount = totalCount,
            categoryId = category.id,
            description = description,
            picture = null,
            userId = null
        )
        _clothingItems.add(newItem)
        _clothingItems.sortBy { it.name }
        clothingRepository.saveClothingItem(newItem)
        updateClosetStatus()
    }

    fun editItem(
        itemToEdit: ClothingItem,
        name: String,
        count: Int,
        totalCount: Int,
        category: ClothingCategory,
        description: String?
    ): ClothingItem {
        val index = _clothingItems.indexOf(itemToEdit)
        _clothingItems[index] = _clothingItems[index].copy(
            name = name,
            count = count,
            totalCount = totalCount,
            categoryId = category.id,
            description = description
        )
        _clothingItems.sortBy { it.name }
        updateClosetStatus()
        clothingRepository.saveClothingItem(_clothingItems[index])
        return _clothingItems[index]
    }

    fun deleteItem(itemToDelete: ClothingItem) {
        _clothingItems.remove(itemToDelete)
        clothingRepository.deleteClothingItem(itemToDelete)
        updateClosetStatus()
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
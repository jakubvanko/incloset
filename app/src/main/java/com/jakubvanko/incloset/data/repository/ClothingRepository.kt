package com.jakubvanko.incloset.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jakubvanko.incloset.domain.model.ClothingCategory
import com.jakubvanko.incloset.domain.model.ClothingItem
import com.jakubvanko.incloset.presentation.ClothingViewModel
import java.util.*

enum class SettingsAction {
    EditCategory, EditItem, CreateCategory, CreateItem,
}

class ClothingRepository(var clothingViewModel: ClothingViewModel) {
    var db = Firebase.firestore

    fun getClothingItems() {
        Log.e("ClothingRepository", "Getting items" + FirebaseAuth.getInstance().currentUser)
        db.collection("items")
            .whereEqualTo("userId", FirebaseAuth.getInstance().currentUser!!.uid)
            .get()
            .addOnSuccessListener { result ->
                clothingViewModel.setClothingItems(result.toObjects(ClothingItem::class.java))
            }
    }

    fun getClothingCategories() {
        db.collection("categories")
            .whereEqualTo("userId", FirebaseAuth.getInstance().currentUser!!.uid)
            .get()
            .addOnSuccessListener { result ->
                clothingViewModel.setClothingCategories(result.toObjects(ClothingCategory::class.java))
            }
    }

    fun saveClothingItem(clothingItem: ClothingItem) {
        clothingItem.userId = FirebaseAuth.getInstance().currentUser!!.uid
        db.collection("items")
            .document(clothingItem.id)
            .set(clothingItem)
    }

    fun saveClothingCategory(clothingCategory: ClothingCategory) {
        clothingCategory.userId = FirebaseAuth.getInstance().currentUser!!.uid
        db.collection("categories")
            .document(clothingCategory.id)
            .set(clothingCategory)
    }

    fun deleteClothingItem(clothingItem: ClothingItem) {
        db.collection("items")
            .document(clothingItem.id)
            .delete()
    }

    fun deleteClothingCategory(clothingCategory: ClothingCategory) {
        db.collection("categories")
            .document(clothingCategory.id)
            .delete()
    }

    fun getMockedData(): Pair<List<ClothingItem>, List<ClothingCategory>> {
        return getItemsMock()
    }

    fun generateId(): String {
        return UUID.randomUUID().toString()
    }


}

fun getItemsMock(): Pair<List<ClothingItem>, List<ClothingCategory>> {
    val clothingCategories = mutableListOf(
        ClothingCategory("1", "T-shirts", 1, null),
        ClothingCategory("2", "Trousers", 1, null),
        ClothingCategory("3", "Underwear", 2, null)
    )
    val clothingItems = mutableListOf(
        ClothingItem("1", "Black T-shirt", 1, 1, "1", "With a cat", null, null),
        ClothingItem("2", "Red T-shirt", 1, 1, "1", "With stars", null, null),
        ClothingItem("3", "Dark green T-shirt", 1, 1, "1", null, null, null),
        ClothingItem("4", "Basic T-shirt", 3, 4, "1", null, null, null),
        ClothingItem("5", "Uncommon T-shirt", 0, 2, "1", null, null, null),
        ClothingItem("6", "Whitewashed jeans", 1, 1, "2", "Informal", null, null),
        ClothingItem("7", "Black jeans", 2, 3, "2", "For everyday usage", null, null),
        ClothingItem("8", "Underwear", 8, 12, "3", "Boxers and briefs", null, null)
    )
    return Pair(clothingItems, clothingCategories)
}
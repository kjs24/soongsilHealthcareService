package com.ssu.soongsilhealthcare.core.model

data class DietRecord(
    val id: Int = 0,
    val userId: String = "",
    val date: String = "",
    val foodName: String = "",
    val calorie: Int = 0,
    val carbohydrate: Int = 0,
    val protein: Int = 0,
    val fat: Int = 0,
    val isFavorite: Boolean = false
)

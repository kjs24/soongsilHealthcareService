package com.ssu.soongsilhealthcare.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diets")
data class DietEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String,
    val date: String,
    val foodName: String,
    val calorie: Int,
    val carbohydrate: Int,
    val protein: Int,
    val fat: Int,
    val isFavorite: Boolean
)

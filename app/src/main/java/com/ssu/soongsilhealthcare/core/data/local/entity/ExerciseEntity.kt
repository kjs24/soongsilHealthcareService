package com.ssu.soongsilhealthcare.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String,
    val date: String,
    val exerciseName: String,
    val setCount: Int,
    val repCount: Int,
    val weight: Double,
    val calorie: Int
)

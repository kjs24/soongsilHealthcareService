package com.ssu.soongsilhealthcare.core.model

data class ExerciseRecord(
    val id: Int = 0,
    val userId: String = "",
    val date: String = "",
    val exerciseName: String = "",
    val setCount: Int = 0,
    val repCount: Int = 0,
    val weight: Double = 0.0,
    val calorie: Int = 0
)

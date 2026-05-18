package com.ssu.soongsilhealthcare.core.util

object CalorieCalculator {
    fun calculateExerciseCalorie(
        setCount: Int,
        repCount: Int,
        weight: Double
    ): Int {
        return (setCount * repCount * weight * 0.05).toInt()
    }
}

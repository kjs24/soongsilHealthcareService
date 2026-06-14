package com.ssu.soongsilhealthcare.feature.exercise

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.soongsilhealthcare.core.data.local.AppDatabase
import com.ssu.soongsilhealthcare.core.data.local.entity.ExerciseEntity
import com.ssu.soongsilhealthcare.core.data.repository.ExerciseRepository
import com.ssu.soongsilhealthcare.core.util.CalorieCalculator
import com.ssu.soongsilhealthcare.core.util.DateUtil
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExerciseViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ExerciseRepository(
        AppDatabase.getInstance(application).exerciseDao()
    )
    private val userId = "demo_user"
    val today = DateUtil.today()

    val exercises: StateFlow<List<ExerciseEntity>> = repository
        .getExercisesByDate(userId, today)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun addExercise(
        exerciseName: String,
        setCountText: String,
        repCountText: String,
        weightText: String
    ) {
        val setCount = setCountText.toIntOrNull() ?: 0
        val repCount = repCountText.toIntOrNull() ?: 0
        val weight = weightText.toDoubleOrNull() ?: 0.0
        val name = exerciseName.trim().ifBlank { "이름 없는 운동" }
        val calorie = CalorieCalculator.calculateExerciseCalorie(setCount, repCount, weight)

        viewModelScope.launch {
            repository.addExercise(
                ExerciseEntity(
                    userId = userId,
                    date = today,
                    exerciseName = name,
                    setCount = setCount,
                    repCount = repCount,
                    weight = weight,
                    calorie = calorie
                )
            )
        }
    }

    fun deleteExercise(exercise: ExerciseEntity) {
        viewModelScope.launch {
            repository.deleteExercise(exercise)
        }
    }
}

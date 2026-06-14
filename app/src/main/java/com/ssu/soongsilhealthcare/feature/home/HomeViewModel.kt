package com.ssu.soongsilhealthcare.feature.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.soongsilhealthcare.core.data.local.AppDatabase
import com.ssu.soongsilhealthcare.core.data.repository.DietRepository
import com.ssu.soongsilhealthcare.core.data.repository.ExerciseRepository
import com.ssu.soongsilhealthcare.core.util.DateUtil
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class HomeSummaryUiState(
    val exerciseCalorie: Int = 0,
    val dietCalorie: Int = 0
)

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getInstance(application)
    private val exerciseRepository = ExerciseRepository(database.exerciseDao())
    private val dietRepository = DietRepository(database.dietDao())
    private val userId = "demo_user"
    private val today = DateUtil.today()

    val summary: StateFlow<HomeSummaryUiState> = combine(
        exerciseRepository.getExercisesByDate(userId, today).map { exercises ->
            exercises.sumOf { it.calorie }
        },
        dietRepository.getDietsByDate(userId, today).map { diets ->
            diets.sumOf { it.calorie }
        }
    ) { exerciseCalorie, dietCalorie ->
        HomeSummaryUiState(
            exerciseCalorie = exerciseCalorie,
            dietCalorie = dietCalorie
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), HomeSummaryUiState())
}

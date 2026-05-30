package com.ssu.soongsilhealthcare.feature.diet

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.soongsilhealthcare.core.data.local.AppDatabase
import com.ssu.soongsilhealthcare.core.data.local.entity.DietEntity
import com.ssu.soongsilhealthcare.core.data.repository.DietRepository
import com.ssu.soongsilhealthcare.core.util.CalorieCalculator
import com.ssu.soongsilhealthcare.core.util.DateUtil
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DietViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DietRepository(
        AppDatabase.getInstance(application).dietDao()
    )
    private val userId = "demo_user"
    val today = DateUtil.today()

    val diets: StateFlow<List<DietEntity>> = repository
        .getDietsByDate(userId, today)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun addDiet(
        foodName: String,
        calorieText: String,
        carbohydrateText: String,
        proteinText: String,
        fatText: String,
        isFavorite: Boolean
    ) {
        val calorie = CalorieCalculator.calculateDietCalorie(calorieText.toIntOrNull() ?: 0)
        val carbohydrate = carbohydrateText.toIntOrNull() ?: 0
        val protein = proteinText.toIntOrNull() ?: 0
        val fat = fatText.toIntOrNull() ?: 0
        val name = foodName.trim().ifBlank { "이름 없는 음식" }

        viewModelScope.launch {
            repository.addDiet(
                DietEntity(
                    userId = userId,
                    date = today,
                    foodName = name,
                    calorie = calorie,
                    carbohydrate = carbohydrate,
                    protein = protein,
                    fat = fat,
                    isFavorite = isFavorite
                )
            )
        }
    }

    fun deleteDiet(diet: DietEntity) {
        viewModelScope.launch {
            repository.deleteDiet(diet)
        }
    }

    fun updateFavorite(diet: DietEntity) {
        viewModelScope.launch {
            repository.updateFavorite(diet.id, !diet.isFavorite)
        }
    }
}

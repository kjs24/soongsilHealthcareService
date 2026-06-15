package com.ssu.soongsilhealthcare.feature.diet

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.soongsilhealthcare.core.data.local.AppDatabase
import com.ssu.soongsilhealthcare.core.data.local.entity.DietEntity
import com.ssu.soongsilhealthcare.core.data.remote.firebase.AuthSession
import com.ssu.soongsilhealthcare.core.data.repository.DietRepository
import com.ssu.soongsilhealthcare.core.util.CalorieCalculator
import com.ssu.soongsilhealthcare.core.util.DateUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class DietViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DietRepository(
        AppDatabase.getInstance(application).dietDao()
    )
    private val userId = AuthSession.uid
    private val selectedDate = MutableStateFlow(DateUtil.today())

    val date: StateFlow<String> = selectedDate

    val diets: StateFlow<List<DietEntity>> = selectedDate
        .flatMapLatest { date -> repository.getDietsByDate(userId, date) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val favoriteDiets: StateFlow<List<DietEntity>> = repository
        .getFavoriteDiets(userId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun updateDate(value: String) {
        selectedDate.value = value
    }

    fun addDiet(
        date: String,
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
        val name = foodName.trim().ifBlank { "이름 없는 식단" }

        viewModelScope.launch {
            repository.addDiet(
                DietEntity(
                    userId = userId,
                    date = date.ifBlank { DateUtil.today() },
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

    fun addFromFavorite(diet: DietEntity) {
        viewModelScope.launch {
            repository.addDiet(
                diet.copy(
                    id = 0,
                    date = selectedDate.value,
                    isFavorite = true
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

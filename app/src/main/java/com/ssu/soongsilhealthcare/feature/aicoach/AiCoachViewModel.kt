package com.ssu.soongsilhealthcare.feature.aicoach

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.soongsilhealthcare.core.data.local.AppDatabase
import com.ssu.soongsilhealthcare.core.data.remote.firebase.AuthSession
import com.ssu.soongsilhealthcare.core.data.remote.firebase.FirestoreService
import com.ssu.soongsilhealthcare.core.data.remote.gemini.GeminiPromptBuilder
import com.ssu.soongsilhealthcare.core.data.repository.AiCoachRepository
import com.ssu.soongsilhealthcare.core.data.repository.DietRepository
import com.ssu.soongsilhealthcare.core.data.repository.ExerciseRepository
import com.ssu.soongsilhealthcare.core.util.DateUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AiCoachUiState(
    val isLoading: Boolean = false,
    val message: String = "",
    val answer: String = "AI 분석 버튼을 누르면 프로필, 오늘 운동 기록, 식단 기록을 바탕으로 코칭을 요청합니다."
)

class AiCoachViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getInstance(application)
    private val exerciseRepository = ExerciseRepository(database.exerciseDao())
    private val dietRepository = DietRepository(database.dietDao())
    private val aiCoachRepository = AiCoachRepository()
    private val firestoreService = FirestoreService()
    private val userId = AuthSession.uid

    private val _uiState = MutableStateFlow(AiCoachUiState())
    val uiState: StateFlow<AiCoachUiState> = _uiState.asStateFlow()

    val isGeminiConfigured: Boolean
        get() = aiCoachRepository.isConfigured

    fun requestCoach() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = "") }
            runCatching {
                val today = DateUtil.today()
                val exercises = exerciseRepository.getExercisesByDate(userId, today).first()
                val diets = dietRepository.getDietsByDate(userId, today).first()
                val profile = if (AuthSession.isFirebaseUser) {
                    firestoreService.getUserProfile(AuthSession.uid, AuthSession.idToken)
                } else {
                    null
                }
                val prompt = GeminiPromptBuilder.buildHealthCoachPrompt(profile, exercises, diets)
                aiCoachRepository.requestCoach(prompt)
            }.onSuccess { answer ->
                _uiState.update { it.copy(isLoading = false, answer = answer) }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        message = error.message ?: "AI 요청에 실패했습니다."
                    )
                }
            }
        }
    }
}

package com.ssu.soongsilhealthcare.feature.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.soongsilhealthcare.core.data.remote.firebase.AuthSession
import com.ssu.soongsilhealthcare.core.data.remote.firebase.FirestoreService
import com.ssu.soongsilhealthcare.core.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MyPageUiState(
    val nickname: String = AuthSession.nickname,
    val height: String = "",
    val weight: String = "",
    val goalWeight: String = "",
    val isLoading: Boolean = false,
    val message: String = ""
)

class MyPageViewModel(
    private val firestoreService: FirestoreService = FirestoreService()
) : ViewModel() {
    private val _uiState = MutableStateFlow(MyPageUiState())
    val uiState: StateFlow<MyPageUiState> = _uiState.asStateFlow()

    val isFirebaseUser: Boolean
        get() = AuthSession.isFirebaseUser

    init {
        loadProfile()
    }

    fun updateNickname(value: String) = _uiState.update { it.copy(nickname = value) }
    fun updateHeight(value: String) = _uiState.update { it.copy(height = value.numberText()) }
    fun updateWeight(value: String) = _uiState.update { it.copy(weight = value.numberText()) }
    fun updateGoalWeight(value: String) = _uiState.update { it.copy(goalWeight = value.numberText()) }

    fun loadProfile() {
        if (!AuthSession.isFirebaseUser) {
            _uiState.update { it.copy(message = "임시 로그인 상태입니다. Firebase 로그인 후 프로필을 저장할 수 있습니다.") }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = "") }
            runCatching { firestoreService.getUserProfile(AuthSession.uid, AuthSession.idToken) }
                .onSuccess { profile ->
                    _uiState.update {
                        it.copy(
                            nickname = profile?.nickname.orEmpty().ifBlank { AuthSession.nickname },
                            height = profile?.height?.takeIf { value -> value > 0.0 }?.toString().orEmpty(),
                            weight = profile?.weight?.takeIf { value -> value > 0.0 }?.toString().orEmpty(),
                            goalWeight = profile?.goalWeight?.takeIf { value -> value > 0.0 }?.toString().orEmpty(),
                            isLoading = false,
                            message = if (profile == null) "저장된 프로필이 없습니다." else "프로필을 불러왔습니다."
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(isLoading = false, message = error.message ?: "프로필 조회에 실패했습니다.") }
                }
        }
    }

    fun saveProfile() {
        if (!AuthSession.isFirebaseUser) {
            _uiState.update { it.copy(message = "Firebase 로그인 후 프로필을 저장할 수 있습니다.") }
            return
        }
        val state = uiState.value
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = "") }
            runCatching {
                val profile = UserProfile(
                    uid = AuthSession.uid,
                    nickname = state.nickname.trim().ifBlank { AuthSession.nickname },
                    height = state.height.toDoubleOrNull() ?: 0.0,
                    weight = state.weight.toDoubleOrNull() ?: 0.0,
                    goalWeight = state.goalWeight.toDoubleOrNull() ?: 0.0
                )
                firestoreService.saveUserProfile(profile, AuthSession.idToken)
                AuthSession.nickname = profile.nickname
            }.onSuccess {
                _uiState.update { it.copy(isLoading = false, message = "프로필을 저장했습니다.") }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, message = error.message ?: "프로필 저장에 실패했습니다.") }
            }
        }
    }

    private fun String.numberText(): String =
        filter { it.isDigit() || it == '.' }.let { value ->
            if (value.count { it == '.' } <= 1) value else value.dropLast(1)
        }
}

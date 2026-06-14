package com.ssu.soongsilhealthcare.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.soongsilhealthcare.core.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val nickname: String = "",
    val isLoading: Boolean = false,
    val message: String = ""
)

class AuthViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    val isFirebaseConfigured: Boolean
        get() = repository.isConfigured

    fun updateEmail(value: String) = _uiState.update { it.copy(email = value) }
    fun updatePassword(value: String) = _uiState.update { it.copy(password = value) }
    fun updateNickname(value: String) = _uiState.update { it.copy(nickname = value) }

    fun tempLogin(onSuccess: () -> Unit) {
        repository.tempLogin()
        onSuccess()
    }

    fun signIn(onSuccess: () -> Unit) {
        submit(onSuccess) {
            repository.signIn(uiState.value.email, uiState.value.password)
        }
    }

    fun signUp(onSuccess: () -> Unit) {
        submit(onSuccess) {
            repository.signUp(
                email = uiState.value.email,
                password = uiState.value.password,
                nickname = uiState.value.nickname.ifBlank { uiState.value.email.substringBefore("@") }
            )
        }
    }

    private fun submit(onSuccess: () -> Unit, block: suspend () -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = "") }
            runCatching { block() }
                .onSuccess {
                    _uiState.update { it.copy(isLoading = false, message = "성공") }
                    onSuccess()
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(isLoading = false, message = error.message ?: "요청에 실패했습니다.")
                    }
                }
        }
    }
}

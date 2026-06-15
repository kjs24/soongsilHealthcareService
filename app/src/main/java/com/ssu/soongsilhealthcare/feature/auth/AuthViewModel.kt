package com.ssu.soongsilhealthcare.feature.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.soongsilhealthcare.core.data.local.AuthSessionStore
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
    val isAuthenticated: Boolean = false,
    val message: String = ""
)

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AuthRepository(AuthSessionStore(application))

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    val isFirebaseConfigured: Boolean
        get() = repository.isConfigured

    init {
        restoreSession()
    }

    fun updateEmail(value: String) = _uiState.update { it.copy(email = value) }
    fun updatePassword(value: String) = _uiState.update { it.copy(password = value) }
    fun updateNickname(value: String) = _uiState.update { it.copy(nickname = value) }

    fun tempLogin() {
        viewModelScope.launch {
            repository.tempLogin()
            _uiState.update { it.copy(isAuthenticated = true) }
        }
    }

    fun signIn() {
        if (!validateEmailPassword()) return
        submit(successMessage = "로그인되었습니다.") {
            repository.signIn(uiState.value.email, uiState.value.password)
        }
    }

    fun signUp() {
        if (!validateEmailPassword()) return
        submit(successMessage = "회원가입이 완료되었습니다.") {
            repository.signUp(
                email = uiState.value.email,
                password = uiState.value.password,
                nickname = uiState.value.nickname
            )
        }
    }

    private fun restoreSession() {
        viewModelScope.launch {
            val restored = repository.restoreSession()
            if (restored) {
                _uiState.update { it.copy(isAuthenticated = true) }
            }
        }
    }

    private fun submit(
        successMessage: String,
        block: suspend () -> Unit
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = "") }
            runCatching { block() }
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isAuthenticated = true,
                            message = successMessage
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(isLoading = false, message = error.toAuthMessage()) }
                }
        }
    }

    private fun validateEmailPassword(): Boolean {
        val state = uiState.value
        val message = when {
            state.email.isBlank() -> "이메일을 입력하세요."
            "@" !in state.email -> "올바른 이메일 형식이 아닙니다."
            state.password.isBlank() -> "비밀번호를 입력하세요."
            state.password.length < 6 -> "비밀번호는 6자 이상이어야 합니다."
            else -> ""
        }
        if (message.isNotBlank()) {
            _uiState.update { it.copy(message = message) }
            return false
        }
        return true
    }

    private fun Throwable.toAuthMessage(): String {
        val raw = message.orEmpty()
        return when {
            "EMAIL_EXISTS" in raw -> "이미 가입된 이메일입니다."
            "EMAIL_NOT_FOUND" in raw || "INVALID_LOGIN_CREDENTIALS" in raw -> "가입되지 않았거나 비밀번호가 맞지 않습니다."
            "INVALID_PASSWORD" in raw -> "비밀번호가 맞지 않습니다."
            "WEAK_PASSWORD" in raw -> "비밀번호는 6자 이상이어야 합니다."
            "INVALID_EMAIL" in raw -> "올바른 이메일 형식이 아닙니다."
            "TOO_MANY_ATTEMPTS_TRY_LATER" in raw -> "잠시 후 다시 시도하세요."
            raw.isNotBlank() -> raw
            else -> "요청에 실패했습니다."
        }
    }
}

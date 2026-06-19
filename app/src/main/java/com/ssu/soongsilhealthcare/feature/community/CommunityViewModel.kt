package com.ssu.soongsilhealthcare.feature.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.soongsilhealthcare.core.data.remote.firebase.FirestoreService
import com.ssu.soongsilhealthcare.core.data.repository.CommunityRepository
import com.ssu.soongsilhealthcare.core.model.CommunityPost
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CommunityUiState(
    val content: String = "",
    val exerciseSummary: String = "",
    val calorie: String = "",
    val posts: List<CommunityPost> = emptyList(),
    val isLoading: Boolean = false,
    val message: String = ""
)

class CommunityViewModel : ViewModel() {
    private val repository = CommunityRepository(
        firestoreService = FirestoreService()
    )

    private val _uiState = MutableStateFlow(CommunityUiState())
    val uiState: StateFlow<CommunityUiState> = _uiState.asStateFlow()

    val isFirestoreConfigured: Boolean
        get() = repository.isConfigured

    fun updateContent(value: String) = _uiState.update { it.copy(content = value) }
    fun updateExerciseSummary(value: String) = _uiState.update { it.copy(exerciseSummary = value) }
    fun updateCalorie(value: String) = _uiState.update { it.copy(calorie = value.filter(Char::isDigit)) }

    fun loadPosts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = "") }
            runCatching { repository.getPosts() }
                .onSuccess { posts ->
                    _uiState.update { it.copy(isLoading = false, posts = posts) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(isLoading = false, message = error.message ?: "게시글 조회에 실패했습니다.") }
                }
        }
    }

    fun addPost() {
        viewModelScope.launch {
            val current = uiState.value
            if (current.content.isBlank()) {
                _uiState.update { it.copy(message = "게시글 내용을 입력하세요.") }
                return@launch
            }
            _uiState.update { it.copy(isLoading = true, message = "") }
            runCatching {
                repository.addPost(
                    content = current.content,
                    exerciseSummary = current.exerciseSummary,
                    calorie = current.calorie.toIntOrNull() ?: 0
                )
                repository.getPosts()
            }.onSuccess { posts ->
                _uiState.update {
                    it.copy(
                        content = "",
                        exerciseSummary = "",
                        calorie = "",
                        posts = posts,
                        isLoading = false,
                        message = "게시글을 저장했습니다."
                    )
                }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, message = error.message ?: "게시글 저장에 실패했습니다.") }
            }
        }
    }

    fun likePost(post: CommunityPost) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = "") }
            runCatching {
                repository.likePost(post)
                repository.getPosts()
            }.onSuccess { posts ->
                _uiState.update { it.copy(isLoading = false, posts = posts) }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, message = error.message ?: "좋아요 처리에 실패했습니다.") }
            }
        }
    }
}

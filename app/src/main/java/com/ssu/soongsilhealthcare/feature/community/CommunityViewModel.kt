package com.ssu.soongsilhealthcare.feature.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

class CommunityViewModel(
    private val repository: CommunityRepository = CommunityRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow(CommunityUiState())
    val uiState: StateFlow<CommunityUiState> = _uiState.asStateFlow()

    val isFirestoreConfigured: Boolean
        get() = repository.isConfigured

    fun updateContent(value: String) = _uiState.update { it.copy(content = value) }
    fun updateExerciseSummary(value: String) = _uiState.update { it.copy(exerciseSummary = value) }
    fun updateCalorie(value: String) = _uiState.update { it.copy(calorie = value) }

    fun loadPosts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = "") }
            runCatching { repository.getPosts() }
                .onSuccess { posts ->
                    _uiState.update { it.copy(isLoading = false, posts = posts) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(isLoading = false, message = error.message ?: "게시글 조회 실패") }
                }
        }
    }

    fun addPost() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = "") }
            val current = uiState.value
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
                _uiState.update { it.copy(isLoading = false, message = error.message ?: "게시글 저장 실패") }
            }
        }
    }
}

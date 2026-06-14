package com.ssu.soongsilhealthcare.feature.community

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CommunityScreen(
    onBackHomeClick: () -> Unit,
    viewModel: CommunityViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "커뮤니티", style = MaterialTheme.typography.headlineSmall)
        Text(
            text = if (viewModel.isFirestoreConfigured) {
                "Firestore 게시글과 연결되어 있습니다."
            } else {
                "Firebase 프로젝트 ID가 필요합니다."
            }
        )
        OutlinedTextField(
            value = uiState.content,
            onValueChange = viewModel::updateContent,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("게시글 내용") }
        )
        OutlinedTextField(
            value = uiState.exerciseSummary,
            onValueChange = viewModel::updateExerciseSummary,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("운동 요약") }
        )
        OutlinedTextField(
            value = uiState.calorie,
            onValueChange = viewModel::updateCalorie,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("운동 칼로리") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = viewModel::addPost,
                enabled = !uiState.isLoading,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "게시")
            }
            Button(
                onClick = viewModel::loadPosts,
                enabled = !uiState.isLoading,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "불러오기")
            }
        }
        Button(
            onClick = onBackHomeClick,
            enabled = !uiState.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "홈으로")
        }
        if (uiState.message.isNotBlank()) {
            Text(text = uiState.message)
        }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(uiState.posts, key = { it.postId }) { post ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(text = post.nickname, style = MaterialTheme.typography.titleMedium)
                        Text(text = post.content)
                        Text(text = "${post.exerciseSummary} / ${post.calorie} kcal")
                    }
                }
            }
        }
    }
}

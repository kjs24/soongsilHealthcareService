package com.ssu.soongsilhealthcare.feature.aicoach

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AiCoachScreen(
    onBackHomeClick: () -> Unit,
    viewModel: AiCoachViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "AI 코치", style = MaterialTheme.typography.headlineSmall)
        Text(text = if (viewModel.isGeminiConfigured) "Gemini API 연동 준비 완료" else "Gemini API 키를 local.properties에 넣어야 실제 응답을 받을 수 있습니다.")
        Card(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = uiState.answer,
                modifier = Modifier.padding(16.dp)
            )
        }
        Button(
            onClick = viewModel::requestCoach,
            enabled = !uiState.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (uiState.isLoading) "분석 중..." else "AI 분석 요청")
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
    }
}

package com.ssu.soongsilhealthcare.feature.aicoach

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssu.soongsilhealthcare.ui.component.CommonTopBar
import com.ssu.soongsilhealthcare.ui.component.LoadingDialog

@Composable
fun AiCoachScreen(
    onBackHomeClick: () -> Unit,
    viewModel: AiCoachViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LoadingDialog(visible = uiState.isLoading, message = "AI 분석 중입니다.")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CommonTopBar(title = "AI 코치", onBackClick = onBackHomeClick)
        Text(
            text = if (viewModel.isGeminiConfigured) {
                "Gemini API와 연결되어 있습니다."
            } else {
                "Gemini API 키를 local.properties에 넣어야 실제 응답을 받을 수 있습니다."
            }
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 220.dp, max = 360.dp)
        ) {
            Text(
                text = uiState.answer,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            )
        }
        Button(
            onClick = viewModel::requestCoach,
            enabled = !uiState.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "AI 분석 요청")
        }
        if (uiState.message.isNotBlank()) {
            Text(text = uiState.message)
        }
    }
}

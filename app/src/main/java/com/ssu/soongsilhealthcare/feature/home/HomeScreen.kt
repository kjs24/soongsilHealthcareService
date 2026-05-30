package com.ssu.soongsilhealthcare.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
fun HomeScreen(
    onExerciseClick: () -> Unit,
    onDietClick: () -> Unit,
    onAiCoachClick: () -> Unit,
    onCommunityClick: () -> Unit,
    onMyPageClick: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val summary by viewModel.summary.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "숭실 헬스케어",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(text = "오늘의 건강 기록 요약")
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "오늘 운동 총 칼로리: ${summary.exerciseCalorie} kcal")
                Text(text = "오늘 식단 총 칼로리: ${summary.dietCalorie} kcal")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        HomeButton("운동 기록", onExerciseClick)
        HomeButton("식단 기록", onDietClick)
        HomeButton("AI 코치", onAiCoachClick)
        HomeButton("커뮤니티", onCommunityClick)
        HomeButton("마이페이지", onMyPageClick)
    }
}

@Composable
private fun HomeButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = text)
    }
}

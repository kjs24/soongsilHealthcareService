package com.ssu.soongsilhealthcare.feature.exercise

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ExerciseScreen(
    onAddClick: () -> Unit,
    onBackHomeClick: () -> Unit,
    viewModel: ExerciseViewModel = viewModel()
) {
    val exercises by viewModel.exercises.collectAsStateWithLifecycle()
    val totalCalorie = exercises.sumOf { it.calorie }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "운동 기록",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(text = "날짜: ${viewModel.today}")
        Text(text = "오늘 운동 총 칼로리: $totalCalorie kcal")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onAddClick, modifier = Modifier.weight(1f)) {
                Text(text = "운동 기록 추가")
            }
            Button(onClick = onBackHomeClick, modifier = Modifier.weight(1f)) {
                Text(text = "홈으로")
            }
        }
        if (exercises.isEmpty()) {
            Text(text = "오늘 등록된 운동 기록이 없습니다.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(exercises, key = { it.id }) { exercise ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = exercise.exerciseName)
                                Text(text = "${exercise.setCount}세트 x ${exercise.repCount}회")
                                Text(text = "무게 ${exercise.weight} kg / ${exercise.calorie} kcal")
                            }
                            Button(onClick = { viewModel.deleteExercise(exercise) }) {
                                Text(text = "삭제")
                            }
                        }
                    }
                }
            }
        }
    }
}

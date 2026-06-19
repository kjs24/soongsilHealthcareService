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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssu.soongsilhealthcare.ui.component.CommonTopBar

@Composable
fun ExerciseScreen(
    onAddClick: () -> Unit,
    onBackHomeClick: () -> Unit,
    viewModel: ExerciseViewModel = viewModel()
) {
    val exercises by viewModel.exercises.collectAsStateWithLifecycle()
    val date by viewModel.date.collectAsStateWithLifecycle()
    val totalCalorie = exercises.sumOf { it.calorie }
    val totalVolume = exercises.sumOf { (it.setCount * it.repCount * it.weight).toInt() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CommonTopBar(title = "운동 기록", onBackClick = onBackHomeClick)
        OutlinedTextField(
            value = date,
            onValueChange = viewModel::updateDate,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("조회 날짜 yyyy-MM-dd") },
            singleLine = true
        )
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(text = "운동 개수: ${exercises.size}개")
                Text(text = "총 운동량: $totalVolume kg")
                Text(text = "예상 소모 칼로리: $totalCalorie kcal")
            }
        }
        Button(onClick = onAddClick, modifier = Modifier.fillMaxWidth()) {
            Text(text = "운동 추가")
        }
        if (exercises.isEmpty()) {
            Text(text = "선택한 날짜에 등록된 운동 기록이 없습니다.")
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
                                Text(text = exercise.exerciseName, style = MaterialTheme.typography.titleMedium)
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

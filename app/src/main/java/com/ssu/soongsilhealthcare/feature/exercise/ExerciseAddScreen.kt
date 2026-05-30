package com.ssu.soongsilhealthcare.feature.exercise

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ExerciseAddScreen(
    onSaved: () -> Unit,
    viewModel: ExerciseViewModel = viewModel()
) {
    var exerciseName by remember { mutableStateOf("") }
    var setCount by remember { mutableStateOf("") }
    var repCount by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "운동 기록 추가",
            style = MaterialTheme.typography.headlineSmall
        )
        OutlinedTextField(
            value = exerciseName,
            onValueChange = { exerciseName = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("운동명") },
            singleLine = true
        )
        NumberField("세트 수", setCount) { setCount = it }
        NumberField("횟수", repCount) { repCount = it }
        NumberField("무게(kg)", weight) { weight = it }
        Button(
            onClick = {
                viewModel.addExercise(exerciseName, setCount, repCount, weight)
                onSaved()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "저장")
        }
        Button(
            onClick = onSaved,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "취소")
        }
    }
}

@Composable
private fun NumberField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

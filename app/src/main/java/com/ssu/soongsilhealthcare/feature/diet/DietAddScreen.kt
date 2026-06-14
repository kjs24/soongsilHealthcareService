package com.ssu.soongsilhealthcare.feature.diet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DietAddScreen(
    onSaved: () -> Unit,
    viewModel: DietViewModel = viewModel()
) {
    var foodName by remember { mutableStateOf("") }
    var calorie by remember { mutableStateOf("") }
    var carbohydrate by remember { mutableStateOf("") }
    var protein by remember { mutableStateOf("") }
    var fat by remember { mutableStateOf("") }
    var isFavorite by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "식단 기록 추가",
            style = MaterialTheme.typography.headlineSmall
        )
        OutlinedTextField(
            value = foodName,
            onValueChange = { foodName = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("음식명") },
            singleLine = true
        )
        NumberField("칼로리(kcal)", calorie) { calorie = it }
        NumberField("탄수화물(g)", carbohydrate) { carbohydrate = it }
        NumberField("단백질(g)", protein) { protein = it }
        NumberField("지방(g)", fat) { fat = it }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isFavorite,
                onCheckedChange = { isFavorite = it }
            )
            Text(text = "즐겨찾기")
        }
        Button(
            onClick = {
                viewModel.addDiet(foodName, calorie, carbohydrate, protein, fat, isFavorite)
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

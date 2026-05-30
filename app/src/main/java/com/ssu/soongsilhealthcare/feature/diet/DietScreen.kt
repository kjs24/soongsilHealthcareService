package com.ssu.soongsilhealthcare.feature.diet

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
fun DietScreen(
    onAddClick: () -> Unit,
    onBackHomeClick: () -> Unit,
    viewModel: DietViewModel = viewModel()
) {
    val diets by viewModel.diets.collectAsStateWithLifecycle()
    val totalCalorie = diets.sumOf { it.calorie }
    val totalCarbohydrate = diets.sumOf { it.carbohydrate }
    val totalProtein = diets.sumOf { it.protein }
    val totalFat = diets.sumOf { it.fat }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "식단 기록",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(text = "날짜: ${viewModel.today}")
        Text(text = "총 칼로리: $totalCalorie kcal")
        Text(text = "탄수화물 ${totalCarbohydrate}g / 단백질 ${totalProtein}g / 지방 ${totalFat}g")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onAddClick, modifier = Modifier.weight(1f)) {
                Text(text = "식단 기록 추가")
            }
            Button(onClick = onBackHomeClick, modifier = Modifier.weight(1f)) {
                Text(text = "홈으로")
            }
        }
        if (diets.isEmpty()) {
            Text(text = "오늘 등록된 식단 기록이 없습니다.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(diets, key = { it.id }) { diet ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = diet.foodName)
                                    Text(text = "${diet.calorie} kcal")
                                    Text(text = "탄 ${diet.carbohydrate}g / 단 ${diet.protein}g / 지 ${diet.fat}g")
                                }
                                Text(text = if (diet.isFavorite) "즐겨찾기" else "일반")
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(
                                    onClick = { viewModel.updateFavorite(diet) },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(text = if (diet.isFavorite) "즐겨찾기 해제" else "즐겨찾기")
                                }
                                Button(
                                    onClick = { viewModel.deleteDiet(diet) },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(text = "삭제")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

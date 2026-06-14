package com.ssu.soongsilhealthcare.feature.mypage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
fun MyPageScreen(
    onBackHomeClick: () -> Unit,
    viewModel: MyPageViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "마이페이지",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = if (viewModel.isFirebaseUser) {
                "Firestore 사용자 프로필과 연결되어 있습니다."
            } else {
                "임시 로그인 상태입니다."
            }
        )
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = uiState.nickname,
                    onValueChange = viewModel::updateNickname,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("닉네임") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = uiState.height,
                    onValueChange = viewModel::updateHeight,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("키(cm)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                OutlinedTextField(
                    value = uiState.weight,
                    onValueChange = viewModel::updateWeight,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("현재 체중(kg)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                OutlinedTextField(
                    value = uiState.goalWeight,
                    onValueChange = viewModel::updateGoalWeight,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("목표 체중(kg)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }
        }
        Button(
            onClick = viewModel::saveProfile,
            enabled = !uiState.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (uiState.isLoading) "저장 중..." else "프로필 저장")
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

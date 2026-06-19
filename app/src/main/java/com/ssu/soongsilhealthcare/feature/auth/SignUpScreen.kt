package com.ssu.soongsilhealthcare.feature.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssu.soongsilhealthcare.ui.component.LoadingDialog

@Composable
fun SignUpScreen(
    onSignedUp: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isAuthenticated) {
        if (uiState.isAuthenticated) {
            onSignedUp()
        }
    }

    LoadingDialog(visible = uiState.isLoading, message = "회원가입 중입니다.")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "회원가입", style = MaterialTheme.typography.headlineMedium)
        OutlinedTextField(
            value = uiState.nickname,
            onValueChange = viewModel::updateNickname,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("닉네임") },
            singleLine = true
        )
        OutlinedTextField(
            value = uiState.email,
            onValueChange = viewModel::updateEmail,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("이메일") },
            singleLine = true
        )
        OutlinedTextField(
            value = uiState.password,
            onValueChange = viewModel::updatePassword,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("비밀번호") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = viewModel::signUp,
            enabled = !uiState.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Firebase 회원가입")
        }
        Button(
            onClick = onBackClick,
            enabled = !uiState.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "뒤로")
        }
        if (uiState.message.isNotBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = uiState.message)
        }
    }
}

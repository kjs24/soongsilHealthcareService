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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssu.soongsilhealthcare.ui.component.LoadingDialog

@Composable
fun LoginScreen(
    onAuthenticated: () -> Unit,
    onSignUpClick: () -> Unit = {},
    viewModel: AuthViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isAuthenticated) {
        if (uiState.isAuthenticated) {
            onAuthenticated()
        }
    }

    LoadingDialog(visible = uiState.isLoading, message = "로그인 중입니다.")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "숭실 헬스케어", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = if (viewModel.isFirebaseConfigured) {
                "Firebase 계정으로 로그인하세요."
            } else {
                "Firebase 설정 전에는 임시 로그인을 사용할 수 있습니다."
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = uiState.email,
            onValueChange = viewModel::updateEmail,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("이메일") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = uiState.password,
            onValueChange = viewModel::updatePassword,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("비밀번호") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = viewModel::signIn,
            enabled = !uiState.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Firebase 로그인")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = viewModel::tempLogin,
            enabled = !uiState.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "임시 로그인")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onSignUpClick,
            enabled = !uiState.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "회원가입")
        }
        if (uiState.message.isNotBlank()) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = uiState.message)
        }
    }
}

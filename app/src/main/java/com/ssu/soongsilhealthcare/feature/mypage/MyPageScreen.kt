package com.ssu.soongsilhealthcare.feature.mypage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MyPageScreen(
    onBackHomeClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "마이페이지",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(text = "마이페이지 기능은 추후 Firebase 사용자 정보와 연동 예정입니다.")
        Button(
            onClick = onBackHomeClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "홈으로")
        }
    }
}

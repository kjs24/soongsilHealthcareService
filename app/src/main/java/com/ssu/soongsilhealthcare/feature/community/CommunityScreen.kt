package com.ssu.soongsilhealthcare.feature.community

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
fun CommunityScreen(
    onBackHomeClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "커뮤니티",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(text = "커뮤니티 기능은 추후 Cloud Firestore와 연동 예정입니다.")
        Button(
            onClick = onBackHomeClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "홈으로")
        }
    }
}

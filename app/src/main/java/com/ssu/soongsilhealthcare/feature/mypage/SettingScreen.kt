package com.ssu.soongsilhealthcare.feature.mypage

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssu.soongsilhealthcare.core.data.local.SettingsRepository
import com.ssu.soongsilhealthcare.core.model.AppSettings
import com.ssu.soongsilhealthcare.ui.component.CommonTopBar
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@Composable
fun SettingScreen(
    onBackClick: () -> Unit,
    viewModel: SettingsViewModel = viewModel()
) {
    val settings by viewModel.settings.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CommonTopBar(title = "설정", onBackClick = onBackClick)
        SettingRow(
            title = "다크모드",
            checked = settings.darkMode,
            onCheckedChange = viewModel::setDarkMode
        )
        SettingRow(
            title = "운동 알림",
            checked = settings.notificationEnabled,
            onCheckedChange = viewModel::setNotificationEnabled
        )
    }
}

@Composable
private fun SettingRow(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = SettingsRepository(application)

    val settings: StateFlow<AppSettings> = repository.settings.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AppSettings()
    )

    fun setDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            repository.setDarkMode(enabled)
        }
    }

    fun setNotificationEnabled(enabled: Boolean) {
        viewModelScope.launch {
            repository.setNotificationEnabled(enabled)
        }
    }
}

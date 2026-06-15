package com.ssu.soongsilhealthcareservice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.ssu.soongsilhealthcare.core.data.local.SettingsRepository
import com.ssu.soongsilhealthcare.core.model.AppSettings
import com.ssu.soongsilhealthcare.navigation.AppNavGraph
import com.ssu.soongsilhealthcareservice.ui.theme.SoongsilHealthcareServiceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val settingsRepository = SettingsRepository(applicationContext)
        setContent {
            val settings by settingsRepository.settings.collectAsState(initial = AppSettings())
            SoongsilHealthcareServiceTheme(darkTheme = settings.darkMode) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavGraph(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

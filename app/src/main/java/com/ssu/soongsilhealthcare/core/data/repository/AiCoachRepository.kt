package com.ssu.soongsilhealthcare.core.data.repository

import com.ssu.soongsilhealthcare.core.data.remote.gemini.GeminiService

class AiCoachRepository(
    private val geminiService: GeminiService = GeminiService()
) {
    val isConfigured: Boolean
        get() = geminiService.isConfigured

    suspend fun requestCoach(prompt: String): String {
        return geminiService.askCoach(prompt)
    }
}

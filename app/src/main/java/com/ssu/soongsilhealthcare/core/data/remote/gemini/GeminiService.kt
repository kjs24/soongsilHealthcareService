package com.ssu.soongsilhealthcare.core.data.remote.gemini

import com.ssu.soongsilhealthcare.core.data.remote.NetworkJsonClient
import com.ssu.soongsilhealthcareservice.BuildConfig
import org.json.JSONArray
import org.json.JSONObject

private const val GEMINI_MODEL_NAME = "gemini-2.5-flash-lite"

class GeminiService {
    private val apiKey = BuildConfig.GEMINI_API_KEY

    val isConfigured: Boolean
        get() = apiKey.isNotBlank()

    suspend fun askCoach(prompt: String): String {
        check(isConfigured) { "Gemini API key is missing in local.properties." }
        val response = NetworkJsonClient.post(
            // Use "gemini-2.5-flash" instead if response quality matters more than cost/latency.
            url = "https://generativelanguage.googleapis.com/v1beta/models/$GEMINI_MODEL_NAME:generateContent?key=$apiKey",
            body = JSONObject().put(
                "contents",
                JSONArray().put(
                    JSONObject().put(
                        "parts",
                        JSONArray().put(JSONObject().put("text", prompt))
                    )
                )
            )
        )
        return response
            .optJSONArray("candidates")
            ?.optJSONObject(0)
            ?.optJSONObject("content")
            ?.optJSONArray("parts")
            ?.optJSONObject(0)
            ?.optString("text")
            .orEmpty()
            .ifBlank { "AI 응답이 비어 있습니다." }
    }
}

package com.ssu.soongsilhealthcare.core.data.remote.gemini

import com.ssu.soongsilhealthcare.core.data.remote.NetworkJsonClient
import com.ssu.soongsilhealthcareservice.BuildConfig
import org.json.JSONArray
import org.json.JSONObject

class GeminiService {
    private val apiKey = BuildConfig.GEMINI_API_KEY

    val isConfigured: Boolean
        get() = apiKey.isNotBlank()

    suspend fun askCoach(prompt: String): String {
        check(isConfigured) { "Gemini API 키가 local.properties에 없습니다." }
        val response = NetworkJsonClient.post(
            url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=$apiKey",
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

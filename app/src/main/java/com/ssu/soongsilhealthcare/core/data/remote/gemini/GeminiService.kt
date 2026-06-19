package com.ssu.soongsilhealthcare.core.data.remote.gemini

import com.ssu.soongsilhealthcare.core.data.remote.NetworkJsonClient
import com.ssu.soongsilhealthcareservice.BuildConfig
import org.json.JSONArray
import org.json.JSONObject

class GeminiService {
    private val apiKey = BuildConfig.GEMINI_API_KEY

    private val trainerInstruction = """
        너는 전문 헬스 트레이너이자 식단 코치야.
        사용자의 운동 기록, 식단 기록, 질문을 전문적인 지식으로 분석해.
        답변은 항상 한국어로 하고, 초보자도 이해하기 쉽게 설명해.
        무리한 운동, 극단적인 식단, 의학적 진단처럼 위험할 수 있는 조언은 피하고 안전한 방향으로 안내해.
        사용자가 입력한 내용이 부족하면 단정하지 말고 필요한 가정을 짧게 밝힌 뒤 조언해.
    """.trimIndent()

    val isConfigured: Boolean
        get() = apiKey.isNotBlank()

    suspend fun askCoach(prompt: String): String {
        check(isConfigured) { "Gemini API key is missing in local.properties." }

        val finalPrompt = """
            $trainerInstruction

            분석할 사용자 정보와 질문:
            $prompt
        """.trimIndent()

        val response = NetworkJsonClient.post(
            url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash-lite:generateContent?key=$apiKey",
            body = JSONObject().put(
                "contents",
                JSONArray().put(
                    JSONObject().put(
                        "parts",
                        JSONArray().put(JSONObject().put("text", finalPrompt))
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

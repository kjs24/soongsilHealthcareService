package com.ssu.soongsilhealthcare.core.data.remote.gemini

object GeminiPromptBuilder {
    fun buildHealthCoachPrompt(
        exerciseCalorie: Int,
        dietCalorie: Int,
        carbohydrate: Int,
        protein: Int,
        fat: Int
    ): String {
        return """
            너는 헬스 초보자를 위한 친절한 운동 및 식단 코치다.

            오늘의 기록:
            - 운동 소모 칼로리: ${exerciseCalorie}kcal
            - 식단 섭취 칼로리: ${dietCalorie}kcal
            - 탄수화물: ${carbohydrate}g
            - 단백질: ${protein}g
            - 지방: ${fat}g

            아래 항목을 짧고 이해하기 쉬운 한국어로 알려줘.
            1. 오늘 운동 기록 평가
            2. 오늘 식단 균형 평가
            3. 내일 개선하면 좋은 점 2가지
        """.trimIndent()
    }
}

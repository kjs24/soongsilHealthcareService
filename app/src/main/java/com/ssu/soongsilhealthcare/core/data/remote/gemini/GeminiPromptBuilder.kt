package com.ssu.soongsilhealthcare.core.data.remote.gemini

import com.ssu.soongsilhealthcare.core.data.local.entity.DietEntity
import com.ssu.soongsilhealthcare.core.data.local.entity.ExerciseEntity
import com.ssu.soongsilhealthcare.core.model.UserProfile

object GeminiPromptBuilder {
    fun buildHealthCoachPrompt(
        profile: UserProfile?,
        exercises: List<ExerciseEntity>,
        diets: List<DietEntity>
    ): String {
        val exerciseCalorie = exercises.sumOf { it.calorie }
        val dietCalorie = diets.sumOf { it.calorie }
        val carbohydrate = diets.sumOf { it.carbohydrate }
        val protein = diets.sumOf { it.protein }
        val fat = diets.sumOf { it.fat }
        val exerciseLines = exercises.joinToString("\n") {
            "- ${it.exerciseName}: ${it.setCount}세트 x ${it.repCount}회, ${it.weight}kg, ${it.calorie}kcal"
        }.ifBlank { "- 오늘 기록된 운동이 없습니다." }
        val dietLines = diets.joinToString("\n") {
            "- ${it.foodName}: ${it.calorie}kcal, 탄수화물 ${it.carbohydrate}g, 단백질 ${it.protein}g, 지방 ${it.fat}g"
        }.ifBlank { "- 오늘 기록된 식단이 없습니다." }
        val profileText = profile?.let {
            """
            - 닉네임: ${it.nickname}
            - 키: ${it.height}cm
            - 현재 체중: ${it.weight}kg
            - 목표 체중: ${it.goalWeight}kg
            """.trimIndent()
        } ?: "- 저장된 신체 프로필이 없습니다."

        return """
            너는 초보 운동 사용자를 돕는 친절한 헬스케어 코치다.
            답변은 한국어로, 과장 없이 실천 가능한 조언만 제공한다.

            사용자 프로필:
            $profileText

            오늘 운동 기록:
            $exerciseLines

            오늘 식단 기록:
            $dietLines

            오늘 합계:
            - 운동 소모 칼로리: ${exerciseCalorie}kcal
            - 식단 섭취 칼로리: ${dietCalorie}kcal
            - 탄수화물: ${carbohydrate}g
            - 단백질: ${protein}g
            - 지방: ${fat}g

            아래 항목을 짧고 명확하게 작성해줘.
            1. 운동 기록 평가
            2. 식단 균형 평가
            3. 단백질 섭취 관점 피드백
            4. 내일 개선하면 좋은 행동 2가지
        """.trimIndent()
    }
}

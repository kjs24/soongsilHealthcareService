from pathlib import Path

BASE_PACKAGE = "com.ssu.soongsilhealthcare"
BASE_PATH = Path("app/src/main/java/com/ssu/soongsilhealthcare")

packages = [
    "navigation",

    "core/model",
    "core/data/local",
    "core/data/local/dao",
    "core/data/local/entity",
    "core/data/remote/firebase",
    "core/data/remote/gemini",
    "core/data/repository",
    "core/util",

    "ui/component",
    "ui/theme",

    "feature/auth",
    "feature/home",
    "feature/exercise",
    "feature/diet",
    "feature/aicoach",
    "feature/community",
    "feature/mypage",
]

kotlin_files = {
    "navigation/Routes.kt": """package com.ssu.soongsilhealthcare.navigation

object Routes {
    const val LOGIN = "login"
    const val SIGN_UP = "sign_up"
    const val HOME = "home"
    const val EXERCISE = "exercise"
    const val DIET = "diet"
    const val AI_COACH = "ai_coach"
    const val COMMUNITY = "community"
    const val MYPAGE = "mypage"
}
""",

    "navigation/AppNavGraph.kt": """package com.ssu.soongsilhealthcare.navigation

import androidx.compose.runtime.Composable

@Composable
fun AppNavGraph() {
    // TODO: Navigation Compose 연결 예정
}
""",

    "core/model/UserProfile.kt": """package com.ssu.soongsilhealthcare.core.model

data class UserProfile(
    val uid: String = "",
    val nickname: String = "",
    val height: Double = 0.0,
    val weight: Double = 0.0,
    val goalWeight: Double = 0.0
)
""",

    "core/model/ExerciseRecord.kt": """package com.ssu.soongsilhealthcare.core.model

data class ExerciseRecord(
    val id: Int = 0,
    val userId: String = "",
    val date: String = "",
    val exerciseName: String = "",
    val setCount: Int = 0,
    val repCount: Int = 0,
    val weight: Double = 0.0,
    val calorie: Int = 0
)
""",

    "core/model/DietRecord.kt": """package com.ssu.soongsilhealthcare.core.model

data class DietRecord(
    val id: Int = 0,
    val userId: String = "",
    val date: String = "",
    val foodName: String = "",
    val calorie: Int = 0,
    val carbohydrate: Int = 0,
    val protein: Int = 0,
    val fat: Int = 0,
    val isFavorite: Boolean = false
)
""",

    "core/model/CommunityPost.kt": """package com.ssu.soongsilhealthcare.core.model

data class CommunityPost(
    val postId: String = "",
    val userId: String = "",
    val nickname: String = "",
    val content: String = "",
    val exerciseSummary: String = "",
    val calorie: Int = 0,
    val likeCount: Int = 0,
    val createdAt: Long = 0L
)
""",

    "core/data/local/AppDatabase.kt": """package com.ssu.soongsilhealthcare.core.data.local

// TODO: Room Database 설정 예정
abstract class AppDatabase
""",

    "core/data/local/dao/ExerciseDao.kt": """package com.ssu.soongsilhealthcare.core.data.local.dao

// TODO: Exercise DAO 작성 예정
interface ExerciseDao
""",

    "core/data/local/dao/DietDao.kt": """package com.ssu.soongsilhealthcare.core.data.local.dao

// TODO: Diet DAO 작성 예정
interface DietDao
""",

    "core/data/local/entity/ExerciseEntity.kt": """package com.ssu.soongsilhealthcare.core.data.local.entity

// TODO: Room Entity로 변경 예정
data class ExerciseEntity(
    val id: Int = 0
)
""",

    "core/data/local/entity/DietEntity.kt": """package com.ssu.soongsilhealthcare.core.data.local.entity

// TODO: Room Entity로 변경 예정
data class DietEntity(
    val id: Int = 0
)
""",

    "core/data/remote/firebase/AuthService.kt": """package com.ssu.soongsilhealthcare.core.data.remote.firebase

// TODO: Firebase Authentication 연결 예정
class AuthService
""",

    "core/data/remote/firebase/FirestoreService.kt": """package com.ssu.soongsilhealthcare.core.data.remote.firebase

// TODO: Cloud Firestore 연결 예정
class FirestoreService
""",

    "core/data/remote/gemini/GeminiService.kt": """package com.ssu.soongsilhealthcare.core.data.remote.gemini

// TODO: Gemini API 연결 예정
class GeminiService
""",

    "core/data/remote/gemini/GeminiPromptBuilder.kt": """package com.ssu.soongsilhealthcare.core.data.remote.gemini

object GeminiPromptBuilder {
    fun buildHealthCoachPrompt(): String {
        return "TODO: 운동 기록과 식단 기록을 기반으로 AI 코치 프롬프트 생성"
    }
}
""",

    "core/data/repository/AuthRepository.kt": """package com.ssu.soongsilhealthcare.core.data.repository

class AuthRepository
""",

    "core/data/repository/ExerciseRepository.kt": """package com.ssu.soongsilhealthcare.core.data.repository

class ExerciseRepository
""",

    "core/data/repository/DietRepository.kt": """package com.ssu.soongsilhealthcare.core.data.repository

class DietRepository
""",

    "core/data/repository/AiCoachRepository.kt": """package com.ssu.soongsilhealthcare.core.data.repository

class AiCoachRepository
""",

    "core/data/repository/CommunityRepository.kt": """package com.ssu.soongsilhealthcare.core.data.repository

class CommunityRepository
""",

    "core/util/CalorieCalculator.kt": """package com.ssu.soongsilhealthcare.core.util

object CalorieCalculator {
    fun calculateExerciseCalorie(
        setCount: Int,
        repCount: Int,
        weight: Double
    ): Int {
        return (setCount * repCount * weight * 0.05).toInt()
    }
}
""",

    "core/util/DateUtil.kt": """package com.ssu.soongsilhealthcare.core.util

object DateUtil {
    fun today(): String {
        return java.time.LocalDate.now().toString()
    }
}
""",

    "ui/component/CommonButton.kt": """package com.ssu.soongsilhealthcare.ui.component

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CommonButton(
    text: String,
    onClick: () -> Unit
) {
    Button(onClick = onClick) {
        Text(text = text)
    }
}
""",

    "feature/auth/LoginScreen.kt": """package com.ssu.soongsilhealthcare.feature.auth

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun LoginScreen() {
    Text(text = "Login Screen")
}
""",

    "feature/auth/SignUpScreen.kt": """package com.ssu.soongsilhealthcare.feature.auth

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SignUpScreen() {
    Text(text = "Sign Up Screen")
}
""",

    "feature/auth/AuthViewModel.kt": """package com.ssu.soongsilhealthcare.feature.auth

import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel()
""",

    "feature/home/HomeScreen.kt": """package com.ssu.soongsilhealthcare.feature.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen() {
    Text(text = "Home Screen")
}
""",

    "feature/home/HomeViewModel.kt": """package com.ssu.soongsilhealthcare.feature.home

import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel()
""",

    "feature/exercise/ExerciseScreen.kt": """package com.ssu.soongsilhealthcare.feature.exercise

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ExerciseScreen() {
    Text(text = "Exercise Screen")
}
""",

    "feature/exercise/ExerciseAddScreen.kt": """package com.ssu.soongsilhealthcare.feature.exercise

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ExerciseAddScreen() {
    Text(text = "Exercise Add Screen")
}
""",

    "feature/exercise/ExerciseViewModel.kt": """package com.ssu.soongsilhealthcare.feature.exercise

import androidx.lifecycle.ViewModel

class ExerciseViewModel : ViewModel()
""",

    "feature/diet/DietScreen.kt": """package com.ssu.soongsilhealthcare.feature.diet

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DietScreen() {
    Text(text = "Diet Screen")
}
""",

    "feature/diet/DietAddScreen.kt": """package com.ssu.soongsilhealthcare.feature.diet

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DietAddScreen() {
    Text(text = "Diet Add Screen")
}
""",

    "feature/diet/DietViewModel.kt": """package com.ssu.soongsilhealthcare.feature.diet

import androidx.lifecycle.ViewModel

class DietViewModel : ViewModel()
""",

    "feature/aicoach/AiCoachScreen.kt": """package com.ssu.soongsilhealthcare.feature.aicoach

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AiCoachScreen() {
    Text(text = "AI Coach Screen")
}
""",

    "feature/aicoach/AiCoachViewModel.kt": """package com.ssu.soongsilhealthcare.feature.aicoach

import androidx.lifecycle.ViewModel

class AiCoachViewModel : ViewModel()
""",

    "feature/community/CommunityScreen.kt": """package com.ssu.soongsilhealthcare.feature.community

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CommunityScreen() {
    Text(text = "Community Screen")
}
""",

    "feature/community/PostWriteScreen.kt": """package com.ssu.soongsilhealthcare.feature.community

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun PostWriteScreen() {
    Text(text = "Post Write Screen")
}
""",

    "feature/community/CommunityViewModel.kt": """package com.ssu.soongsilhealthcare.feature.community

import androidx.lifecycle.ViewModel

class CommunityViewModel : ViewModel()
""",

    "feature/mypage/MyPageScreen.kt": """package com.ssu.soongsilhealthcare.feature.mypage

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MyPageScreen() {
    Text(text = "My Page Screen")
}
""",

    "feature/mypage/SettingScreen.kt": """package com.ssu.soongsilhealthcare.feature.mypage

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SettingScreen() {
    Text(text = "Setting Screen")
}
""",

    "feature/mypage/MyPageViewModel.kt": """package com.ssu.soongsilhealthcare.feature.mypage

import androidx.lifecycle.ViewModel

class MyPageViewModel : ViewModel()
""",
}

docs = {
    "docs/feature_plan.md": """# 기능 명세서

## 주요 기능

- 로그인/회원가입
- 운동 기록
- 식단 기록
- AI 코치
- 커뮤니티
- 마이페이지/설정
""",

    "docs/db_design.md": """# DB 설계

## Room DB

- ExerciseEntity: 개인 운동 기록 저장
- DietEntity: 개인 식단 기록 저장

## Firestore

- users: 사용자 프로필 저장
- communityPosts: 커뮤니티 게시글 저장
""",

    "docs/firebase_design.md": """# Firebase 설계

## Firebase Authentication

- 이메일/비밀번호 로그인
- 회원가입
- 로그아웃

## Cloud Firestore

- 사용자 프로필
- 커뮤니티 게시글
""",

    "docs/ai_prompt.md": """# AI 코치 프롬프트 설계

Gemini API에 운동 기록, 식단 기록, 사용자 신체 정보를 전달하여 운동량과 식단 균형을 평가한다.
""",
}

def main():
    for package in packages:
        path = BASE_PATH / package
        path.mkdir(parents=True, exist_ok=True)

    for relative_path, content in kotlin_files.items():
        file_path = BASE_PATH / relative_path
        file_path.parent.mkdir(parents=True, exist_ok=True)
        if not file_path.exists():
            file_path.write_text(content, encoding="utf-8")

    for relative_path, content in docs.items():
        file_path = Path(relative_path)
        file_path.parent.mkdir(parents=True, exist_ok=True)
        if not file_path.exists():
            file_path.write_text(content, encoding="utf-8")

    print("프로젝트 폴더 구조 생성 완료!")

if __name__ == "__main__":
    main()

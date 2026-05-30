package com.ssu.soongsilhealthcare.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ssu.soongsilhealthcare.feature.aicoach.AiCoachScreen
import com.ssu.soongsilhealthcare.feature.auth.LoginScreen
import com.ssu.soongsilhealthcare.feature.auth.SignUpScreen
import com.ssu.soongsilhealthcare.feature.community.CommunityScreen
import com.ssu.soongsilhealthcare.feature.diet.DietAddScreen
import com.ssu.soongsilhealthcare.feature.diet.DietScreen
import com.ssu.soongsilhealthcare.feature.exercise.ExerciseAddScreen
import com.ssu.soongsilhealthcare.feature.exercise.ExerciseScreen
import com.ssu.soongsilhealthcare.feature.home.HomeScreen
import com.ssu.soongsilhealthcare.feature.mypage.MyPageScreen

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onTempLoginClick = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onSignUpClick = { navController.navigate(Routes.SIGN_UP) }
            )
        }
        composable(Routes.SIGN_UP) {
            SignUpScreen()
        }
        composable(Routes.HOME) {
            HomeScreen(
                onExerciseClick = { navController.navigate(Routes.EXERCISE) },
                onDietClick = { navController.navigate(Routes.DIET) },
                onAiCoachClick = { navController.navigate(Routes.AI_COACH) },
                onCommunityClick = { navController.navigate(Routes.COMMUNITY) },
                onMyPageClick = { navController.navigate(Routes.MYPAGE) }
            )
        }
        composable(Routes.EXERCISE) {
            ExerciseScreen(
                onAddClick = { navController.navigate(Routes.EXERCISE_ADD) },
                onBackHomeClick = { navController.navigateToHome() }
            )
        }
        composable(Routes.EXERCISE_ADD) {
            ExerciseAddScreen(
                onSaved = { navController.popBackStack() }
            )
        }
        composable(Routes.DIET) {
            DietScreen(
                onAddClick = { navController.navigate(Routes.DIET_ADD) },
                onBackHomeClick = { navController.navigateToHome() }
            )
        }
        composable(Routes.DIET_ADD) {
            DietAddScreen(
                onSaved = { navController.popBackStack() }
            )
        }
        composable(Routes.AI_COACH) {
            AiCoachScreen(onBackHomeClick = { navController.navigateToHome() })
        }
        composable(Routes.COMMUNITY) {
            CommunityScreen(onBackHomeClick = { navController.navigateToHome() })
        }
        composable(Routes.MYPAGE) {
            MyPageScreen(onBackHomeClick = { navController.navigateToHome() })
        }
    }
}

private fun NavHostController.navigateToHome() {
    navigate(Routes.HOME) {
        popUpTo(Routes.HOME) { inclusive = false }
        launchSingleTop = true
    }
}

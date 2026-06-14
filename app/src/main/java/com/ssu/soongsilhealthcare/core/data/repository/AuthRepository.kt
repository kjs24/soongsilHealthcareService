package com.ssu.soongsilhealthcare.core.data.repository

import com.ssu.soongsilhealthcare.core.data.remote.firebase.AuthService
import com.ssu.soongsilhealthcare.core.data.remote.firebase.AuthSession
import com.ssu.soongsilhealthcare.core.data.remote.firebase.FirestoreService
import com.ssu.soongsilhealthcare.core.model.UserProfile

class AuthRepository(
    private val authService: AuthService = AuthService(),
    private val firestoreService: FirestoreService = FirestoreService()
) {
    val isConfigured: Boolean
        get() = authService.isConfigured && firestoreService.isConfigured

    suspend fun signIn(email: String, password: String) {
        val user = authService.signIn(email, password)
        AuthSession.setFirebaseUser(user.uid, user.email, user.email.substringBefore("@"), user.idToken)
    }

    suspend fun signUp(email: String, password: String, nickname: String) {
        val user = authService.signUp(email, password)
        AuthSession.setFirebaseUser(user.uid, user.email, nickname, user.idToken)
        firestoreService.saveUserProfile(
            profile = UserProfile(uid = user.uid, nickname = nickname),
            idToken = user.idToken
        )
    }

    fun tempLogin() {
        AuthSession.useDemoUser()
    }
}

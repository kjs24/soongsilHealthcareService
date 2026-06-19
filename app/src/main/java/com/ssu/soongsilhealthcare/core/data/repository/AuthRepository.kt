package com.ssu.soongsilhealthcare.core.data.repository

import com.ssu.soongsilhealthcare.core.data.local.AuthSessionStore
import com.ssu.soongsilhealthcare.core.data.remote.firebase.AuthService
import com.ssu.soongsilhealthcare.core.data.remote.firebase.AuthSession
import com.ssu.soongsilhealthcare.core.data.remote.firebase.FirestoreService
import com.ssu.soongsilhealthcare.core.model.UserProfile

class AuthRepository(
    private val sessionStore: AuthSessionStore,
    private val authService: AuthService = AuthService(),
    private val firestoreService: FirestoreService = FirestoreService()
) {
    val isConfigured: Boolean
        get() = authService.isConfigured && firestoreService.isConfigured

    suspend fun restoreSession(): Boolean {
        val snapshot = sessionStore.restore() ?: return false
        AuthSession.restore(snapshot)
        return true
    }

    suspend fun signIn(email: String, password: String) {
        val user = authService.signIn(email.trim(), password)
        val profile = firestoreService.getUserProfile(user.uid, user.idToken)
        val nickname = profile?.nickname.orEmpty().ifBlank { user.email.substringBefore("@") }
        AuthSession.setFirebaseUser(
            uid = user.uid,
            email = user.email,
            nickname = nickname,
            idToken = user.idToken,
            refreshToken = user.refreshToken
        )
        sessionStore.save(AuthSession.snapshot())
    }

    suspend fun signUp(email: String, password: String, nickname: String) {
        val cleanEmail = email.trim()
        val cleanNickname = nickname.trim().ifBlank { cleanEmail.substringBefore("@") }
        val user = authService.signUp(cleanEmail, password)
        AuthSession.setFirebaseUser(
            uid = user.uid,
            email = user.email,
            nickname = cleanNickname,
            idToken = user.idToken,
            refreshToken = user.refreshToken
        )
        firestoreService.saveUserProfile(
            profile = UserProfile(uid = user.uid, nickname = cleanNickname),
            idToken = user.idToken
        )
        sessionStore.save(AuthSession.snapshot())
    }

    suspend fun tempLogin() {
        AuthSession.useDemoUser()
        sessionStore.clear()
    }

    suspend fun signOut() {
        AuthSession.clear()
        sessionStore.clear()
    }
}

package com.ssu.soongsilhealthcare.core.data.remote.firebase

import com.ssu.soongsilhealthcare.core.model.AuthSessionSnapshot

object AuthSession {
    var uid: String = "demo_user"
    var email: String = ""
    var nickname: String = "demo_user"
    var idToken: String = ""
    var refreshToken: String = ""

    val isFirebaseUser: Boolean
        get() = idToken.isNotBlank()

    fun setFirebaseUser(
        uid: String,
        email: String,
        nickname: String,
        idToken: String,
        refreshToken: String
    ) {
        this.uid = uid
        this.email = email
        this.nickname = nickname.ifBlank { email.substringBefore("@").ifBlank { "user" } }
        this.idToken = idToken
        this.refreshToken = refreshToken
    }

    fun restore(snapshot: AuthSessionSnapshot) {
        uid = snapshot.uid
        email = snapshot.email
        nickname = snapshot.nickname
        idToken = snapshot.idToken
        refreshToken = snapshot.refreshToken
    }

    fun useDemoUser() {
        uid = "demo_user"
        email = ""
        nickname = "demo_user"
        idToken = ""
        refreshToken = ""
    }

    fun snapshot(): AuthSessionSnapshot =
        AuthSessionSnapshot(
            uid = uid,
            email = email,
            nickname = nickname,
            idToken = idToken,
            refreshToken = refreshToken
        )

    fun clear() {
        useDemoUser()
    }
}

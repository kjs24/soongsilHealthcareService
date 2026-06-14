package com.ssu.soongsilhealthcare.core.data.remote.firebase

object AuthSession {
    var uid: String = "demo_user"
    var email: String = ""
    var nickname: String = "demo_user"
    var idToken: String = ""

    val isFirebaseUser: Boolean
        get() = idToken.isNotBlank()

    fun setFirebaseUser(uid: String, email: String, nickname: String, idToken: String) {
        this.uid = uid
        this.email = email
        this.nickname = nickname.ifBlank { email.substringBefore("@").ifBlank { "user" } }
        this.idToken = idToken
    }

    fun useDemoUser() {
        uid = "demo_user"
        email = ""
        nickname = "demo_user"
        idToken = ""
    }
}

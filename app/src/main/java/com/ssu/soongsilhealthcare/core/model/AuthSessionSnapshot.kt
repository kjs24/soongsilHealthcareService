package com.ssu.soongsilhealthcare.core.model

data class AuthSessionSnapshot(
    val uid: String,
    val email: String,
    val nickname: String,
    val idToken: String,
    val refreshToken: String
) {
    val isFirebaseUser: Boolean
        get() = idToken.isNotBlank()
}

package com.ssu.soongsilhealthcare.core.model

data class UserProfile(
    val uid: String = "",
    val nickname: String = "",
    val height: Double = 0.0,
    val weight: Double = 0.0,
    val goalWeight: Double = 0.0
)

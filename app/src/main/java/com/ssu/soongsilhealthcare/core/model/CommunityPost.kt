package com.ssu.soongsilhealthcare.core.model

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

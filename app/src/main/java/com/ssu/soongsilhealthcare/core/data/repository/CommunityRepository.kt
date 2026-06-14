package com.ssu.soongsilhealthcare.core.data.repository

import com.ssu.soongsilhealthcare.core.data.remote.firebase.AuthSession
import com.ssu.soongsilhealthcare.core.data.remote.firebase.FirestoreService
import com.ssu.soongsilhealthcare.core.model.CommunityPost

class CommunityRepository(
    private val firestoreService: FirestoreService = FirestoreService()
) {
    val isConfigured: Boolean
        get() = firestoreService.isConfigured

    suspend fun getPosts(): List<CommunityPost> {
        check(AuthSession.isFirebaseUser) { "Firebase 로그인 후 커뮤니티를 사용할 수 있습니다." }
        return firestoreService.getCommunityPosts(AuthSession.idToken)
    }

    suspend fun addPost(content: String, exerciseSummary: String, calorie: Int) {
        check(AuthSession.isFirebaseUser) { "Firebase 로그인 후 게시글을 작성할 수 있습니다." }
        check(content.isNotBlank()) { "게시글 내용을 입력하세요." }

        firestoreService.createCommunityPost(
            post = CommunityPost(
                userId = AuthSession.uid,
                nickname = AuthSession.nickname,
                content = content.trim(),
                exerciseSummary = exerciseSummary.trim(),
                calorie = calorie,
                likeCount = 0,
                createdAt = System.currentTimeMillis()
            ),
            idToken = AuthSession.idToken
        )
    }
}

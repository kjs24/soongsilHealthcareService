package com.ssu.soongsilhealthcare.core.data.remote.firebase

import com.ssu.soongsilhealthcare.core.data.remote.NetworkJsonClient
import com.ssu.soongsilhealthcare.core.model.CommunityPost
import com.ssu.soongsilhealthcare.core.model.UserProfile
import com.ssu.soongsilhealthcareservice.BuildConfig
import org.json.JSONObject

class FirestoreService {
    private val projectId = BuildConfig.FIREBASE_PROJECT_ID

    val isConfigured: Boolean
        get() = projectId.isNotBlank()

    suspend fun saveUserProfile(profile: UserProfile, idToken: String) {
        check(isConfigured) { "Firebase 프로젝트 ID가 local.properties에 없습니다." }
        NetworkJsonClient.patch(
            url = "$baseUrl/users/${profile.uid}",
            idToken = idToken,
            body = JSONObject().put(
                "fields",
                JSONObject()
                    .put("nickname", stringField(profile.nickname))
                    .put("height", doubleField(profile.height))
                    .put("weight", doubleField(profile.weight))
                    .put("goalWeight", doubleField(profile.goalWeight))
                    .put("createdAt", integerField(System.currentTimeMillis()))
            )
        )
    }

    suspend fun createCommunityPost(post: CommunityPost, idToken: String) {
        check(isConfigured) { "Firebase 프로젝트 ID가 local.properties에 없습니다." }
        NetworkJsonClient.post(
            url = "$baseUrl/communityPosts",
            idToken = idToken,
            body = JSONObject().put(
                "fields",
                JSONObject()
                    .put("userId", stringField(post.userId))
                    .put("nickname", stringField(post.nickname))
                    .put("content", stringField(post.content))
                    .put("exerciseSummary", stringField(post.exerciseSummary))
                    .put("calorie", integerField(post.calorie.toLong()))
                    .put("likeCount", integerField(post.likeCount.toLong()))
                    .put("createdAt", integerField(post.createdAt))
            )
        )
    }

    suspend fun getCommunityPosts(idToken: String): List<CommunityPost> {
        check(isConfigured) { "Firebase 프로젝트 ID가 local.properties에 없습니다." }
        val response = NetworkJsonClient.get("$baseUrl/communityPosts?pageSize=20", idToken)
        val documents = response.optJSONArray("documents") ?: return emptyList()
        return List(documents.length()) { index ->
            val document = documents.getJSONObject(index)
            val fields = document.optJSONObject("fields") ?: JSONObject()
            CommunityPost(
                postId = document.optString("name").substringAfterLast("/"),
                userId = fields.stringValue("userId"),
                nickname = fields.stringValue("nickname"),
                content = fields.stringValue("content"),
                exerciseSummary = fields.stringValue("exerciseSummary"),
                calorie = fields.integerValue("calorie").toInt(),
                likeCount = fields.integerValue("likeCount").toInt(),
                createdAt = fields.integerValue("createdAt")
            )
        }.sortedByDescending { it.createdAt }
    }

    private val baseUrl: String
        get() = "https://firestore.googleapis.com/v1/projects/$projectId/databases/(default)/documents"

    private fun stringField(value: String) = JSONObject().put("stringValue", value)
    private fun integerField(value: Long) = JSONObject().put("integerValue", value.toString())
    private fun doubleField(value: Double) = JSONObject().put("doubleValue", value)

    private fun JSONObject.stringValue(name: String): String =
        optJSONObject(name)?.optString("stringValue").orEmpty()

    private fun JSONObject.integerValue(name: String): Long =
        optJSONObject(name)?.optString("integerValue")?.toLongOrNull() ?: 0L
}

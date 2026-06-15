package com.ssu.soongsilhealthcare.core.data.remote.firebase

import com.ssu.soongsilhealthcare.core.data.remote.NetworkJsonClient
import com.ssu.soongsilhealthcare.core.model.CommunityPost
import com.ssu.soongsilhealthcare.core.model.UserProfile
import com.ssu.soongsilhealthcareservice.BuildConfig
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class FirestoreService {
    private val projectId = BuildConfig.FIREBASE_PROJECT_ID

    val isConfigured: Boolean
        get() = projectId.isNotBlank()

    suspend fun getUserProfile(uid: String, idToken: String): UserProfile? {
        check(isConfigured) { "Firebase project id is missing in local.properties." }
        val response = runCatching {
            NetworkJsonClient.get("$baseUrl/users/$uid", idToken)
        }.getOrElse { return null }
        val fields = response.optJSONObject("fields") ?: return null
        return UserProfile(
            uid = uid,
            nickname = fields.stringValue("nickname"),
            height = fields.doubleValue("height"),
            weight = fields.doubleValue("weight"),
            goalWeight = fields.doubleValue("goalWeight"),
            createdAt = fields.timestampMillis("createdAt"),
            updatedAt = fields.timestampMillis("updatedAt")
        )
    }

    suspend fun saveUserProfile(profile: UserProfile, idToken: String) {
        check(isConfigured) { "Firebase project id is missing in local.properties." }
        val now = System.currentTimeMillis()
        val createdAt = profile.createdAt.takeIf { it > 0L } ?: now
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
                    .put("createdAt", timestampField(createdAt))
                    .put("updatedAt", timestampField(now))
            )
        )
    }

    suspend fun createCommunityPost(post: CommunityPost, idToken: String) {
        check(isConfigured) { "Firebase project id is missing in local.properties." }
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
                    .put("createdAt", timestampField(post.createdAt))
            )
        )
    }

    suspend fun getCommunityPosts(idToken: String): List<CommunityPost> {
        check(isConfigured) { "Firebase project id is missing in local.properties." }
        val response = NetworkJsonClient.get("$baseUrl/communityPosts?pageSize=50", idToken)
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
                createdAt = fields.timestampMillis("createdAt")
            )
        }.sortedByDescending { it.createdAt }
    }

    suspend fun updateCommunityPostLikeCount(postId: String, likeCount: Int, idToken: String) {
        check(isConfigured) { "Firebase project id is missing in local.properties." }
        NetworkJsonClient.patch(
            url = "$baseUrl/communityPosts/$postId?updateMask.fieldPaths=likeCount",
            idToken = idToken,
            body = JSONObject().put(
                "fields",
                JSONObject().put("likeCount", integerField(likeCount.toLong()))
            )
        )
    }

    private val baseUrl: String
        get() = "https://firestore.googleapis.com/v1/projects/$projectId/databases/(default)/documents"

    private fun stringField(value: String) = JSONObject().put("stringValue", value)
    private fun integerField(value: Long) = JSONObject().put("integerValue", value.toString())
    private fun doubleField(value: Double) = JSONObject().put("doubleValue", value)
    private fun timestampField(value: Long) = JSONObject().put("timestampValue", value.toFirestoreTimestamp())

    private fun JSONObject.stringValue(name: String): String =
        optJSONObject(name)?.optString("stringValue").orEmpty()

    private fun JSONObject.integerValue(name: String): Long =
        optJSONObject(name)?.optString("integerValue")?.toLongOrNull() ?: 0L

    private fun JSONObject.doubleValue(name: String): Double {
        val field = optJSONObject(name) ?: return 0.0
        return when {
            field.has("doubleValue") -> field.optDouble("doubleValue")
            field.has("integerValue") -> field.optString("integerValue").toDoubleOrNull() ?: 0.0
            else -> 0.0
        }
    }

    private fun JSONObject.timestampMillis(name: String): Long {
        val field = optJSONObject(name) ?: return 0L
        field.optString("integerValue").toLongOrNull()?.let { return it }
        val timestamp = field.optString("timestampValue")
        return if (timestamp.isBlank()) 0L else runCatching {
            firestoreTimestampFormat.parse(timestamp)?.time ?: 0L
        }.getOrDefault(0L)
    }

    private fun Long.toFirestoreTimestamp(): String =
        firestoreTimestampFormat.format(Date(this))

    private companion object {
        val firestoreTimestampFormat: SimpleDateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
    }
}

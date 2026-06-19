package com.ssu.soongsilhealthcare.core.data.remote.firebase

import com.ssu.soongsilhealthcare.core.data.remote.NetworkJsonClient
import com.ssu.soongsilhealthcareservice.BuildConfig
import org.json.JSONObject

data class FirebaseAuthUser(
    val uid: String,
    val email: String,
    val idToken: String,
    val refreshToken: String
)

class AuthService {
    private val apiKey = BuildConfig.FIREBASE_API_KEY

    val isConfigured: Boolean
        get() = apiKey.isNotBlank()

    suspend fun signUp(email: String, password: String): FirebaseAuthUser {
        return authenticate("signUp", email, password)
    }

    suspend fun signIn(email: String, password: String): FirebaseAuthUser {
        return authenticate("signInWithPassword", email, password)
    }

    private suspend fun authenticate(action: String, email: String, password: String): FirebaseAuthUser {
        check(isConfigured) { "Firebase API key is missing in local.properties." }

        val response = NetworkJsonClient.post(
            url = "https://identitytoolkit.googleapis.com/v1/accounts:$action?key=$apiKey",
            body = JSONObject()
                .put("email", email)
                .put("password", password)
                .put("returnSecureToken", true)
        )

        return FirebaseAuthUser(
            uid = response.getString("localId"),
            email = response.optString("email", email),
            idToken = response.getString("idToken"),
            refreshToken = response.optString("refreshToken")
        )
    }
}

package com.ssu.soongsilhealthcare.core.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Url
import java.util.concurrent.TimeUnit

object NetworkJsonClient {
    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()
    private val formMediaType = "application/x-www-form-urlencoded".toMediaType()
    private val binaryMediaType = "application/octet-stream".toMediaType()

    private val api: RawApi = Retrofit.Builder()
        .baseUrl("https://example.com/")
        .client(
            OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build()
        )
        .build()
        .create(RawApi::class.java)

    suspend fun get(url: String, idToken: String = ""): JSONObject =
        request { api.get(url, bearer(idToken)) }

    suspend fun post(url: String, body: JSONObject, idToken: String = ""): JSONObject =
        request { api.post(url, bearer(idToken), body.toString().toRequestBody(jsonMediaType)) }

    suspend fun postForm(url: String, formBody: String): JSONObject =
        request { api.post(url, null, formBody.toRequestBody(formMediaType)) }

    suspend fun postBytes(
        url: String,
        bytes: ByteArray,
        contentType: String,
        idToken: String = ""
    ): JSONObject {
        val mediaType = contentType.toMediaTypeOrNull() ?: binaryMediaType
        return request { api.post(url, bearer(idToken), bytes.toRequestBody(mediaType)) }
    }

    suspend fun patch(url: String, body: JSONObject, idToken: String = ""): JSONObject =
        request { api.patch(url, bearer(idToken), body.toString().toRequestBody(jsonMediaType)) }

    private suspend fun request(call: suspend () -> Response<ResponseBody>): JSONObject =
        withContext(Dispatchers.IO) {
            val response = call()
            val text = if (response.isSuccessful) {
                response.body()?.string().orEmpty()
            } else {
                response.errorBody()?.string().orEmpty()
            }
            val json = text.takeIf { it.isNotBlank() }?.let(::JSONObject) ?: JSONObject()
            if (!response.isSuccessful) {
                val message = json.optJSONObject("error")?.optString("message").orEmpty()
                throw IllegalStateException(message.ifBlank { "Network error: ${response.code()}" })
            }
            json
        }

    private fun bearer(idToken: String): String? =
        idToken.takeIf { it.isNotBlank() }?.let { "Bearer $it" }

    private interface RawApi {
        @GET
        suspend fun get(
            @Url url: String,
            @Header("Authorization") authorization: String?
        ): Response<ResponseBody>

        @POST
        suspend fun post(
            @Url url: String,
            @Header("Authorization") authorization: String?,
            @Body body: RequestBody
        ): Response<ResponseBody>

        @PATCH
        suspend fun patch(
            @Url url: String,
            @Header("Authorization") authorization: String?,
            @Body body: RequestBody
        ): Response<ResponseBody>
    }
}

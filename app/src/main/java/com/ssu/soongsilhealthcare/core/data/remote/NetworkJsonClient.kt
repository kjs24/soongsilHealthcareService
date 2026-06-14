package com.ssu.soongsilhealthcare.core.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

object NetworkJsonClient {
    suspend fun get(url: String, idToken: String = ""): JSONObject = request("GET", url, null, idToken)

    suspend fun post(url: String, body: JSONObject, idToken: String = ""): JSONObject =
        request("POST", url, body, idToken)

    suspend fun patch(url: String, body: JSONObject, idToken: String = ""): JSONObject =
        request("PATCH", url, body, idToken)

    private suspend fun request(
        method: String,
        url: String,
        body: JSONObject?,
        idToken: String
    ): JSONObject = withContext(Dispatchers.IO) {
        val connection = (URL(url).openConnection() as HttpURLConnection).apply {
            requestMethod = method
            connectTimeout = 15_000
            readTimeout = 15_000
            setRequestProperty("Content-Type", "application/json")
            if (idToken.isNotBlank()) {
                setRequestProperty("Authorization", "Bearer $idToken")
            }
            doInput = true
            if (body != null) {
                doOutput = true
            }
        }

        if (body != null) {
            OutputStreamWriter(connection.outputStream).use { it.write(body.toString()) }
        }

        val stream = if (connection.responseCode in 200..299) {
            connection.inputStream
        } else {
            connection.errorStream
        }
        val text = BufferedReader(InputStreamReader(stream)).use { it.readText() }
        val json = if (text.isBlank()) JSONObject() else JSONObject(text)
        if (connection.responseCode !in 200..299) {
            val message = json.optJSONObject("error")?.optString("message").orEmpty()
            throw IllegalStateException(message.ifBlank { "Network error: ${connection.responseCode}" })
        }
        json
    }
}

package com.ssu.soongsilhealthcare.core.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ssu.soongsilhealthcare.core.model.AuthSessionSnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.authSessionDataStore by preferencesDataStore(name = "auth_session")

class AuthSessionStore(context: Context) {
    private val dataStore = context.applicationContext.authSessionDataStore

    val sessionFlow: Flow<AuthSessionSnapshot?> = dataStore.data.map { preferences ->
        val uid = preferences[UID].orEmpty()
        val idToken = preferences[ID_TOKEN].orEmpty()
        if (uid.isBlank() || idToken.isBlank()) {
            null
        } else {
            AuthSessionSnapshot(
                uid = uid,
                email = preferences[EMAIL].orEmpty(),
                nickname = preferences[NICKNAME].orEmpty(),
                idToken = idToken,
                refreshToken = preferences[REFRESH_TOKEN].orEmpty()
            )
        }
    }

    suspend fun restore(): AuthSessionSnapshot? = sessionFlow.first()

    suspend fun save(snapshot: AuthSessionSnapshot) {
        dataStore.edit { preferences ->
            preferences[UID] = snapshot.uid
            preferences[EMAIL] = snapshot.email
            preferences[NICKNAME] = snapshot.nickname
            preferences[ID_TOKEN] = snapshot.idToken
            preferences[REFRESH_TOKEN] = snapshot.refreshToken
        }
    }

    suspend fun clear() {
        dataStore.edit { it.clear() }
    }

    private companion object {
        val UID = stringPreferencesKey("uid")
        val EMAIL = stringPreferencesKey("email")
        val NICKNAME = stringPreferencesKey("nickname")
        val ID_TOKEN = stringPreferencesKey("id_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }
}

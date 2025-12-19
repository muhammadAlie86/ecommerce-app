package com.example.ecommerce.libraries.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name ="ecommerce_datastore")

class DataStoreManager (context: Context) {

    private val dataStore: DataStore<Preferences> = context.dataStore

    private val keyIsLogin = booleanPreferencesKey("isLogin")
    private val keyUserId = intPreferencesKey("user_id")
    private val keyUsername = stringPreferencesKey("username")

    suspend fun saveLoginStatus(isLogin: Boolean) {
        dataStore.edit { preferences ->
            preferences[keyIsLogin] = isLogin
        }
    }

    val isLoggedIn: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[keyIsLogin] ?: false
        }
    suspend fun saveUserId(userId: Int) {
        dataStore.edit { preferences ->
            preferences[keyUserId] = userId
        }
    }

    val getUserId: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[keyUserId] ?: -99
        }

    suspend fun saveUsername(username: String) {
        dataStore.edit { preferences ->
            preferences[keyUsername] = username
        }
    }

    val getUsername: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[keyUsername] ?: ""
        }
}
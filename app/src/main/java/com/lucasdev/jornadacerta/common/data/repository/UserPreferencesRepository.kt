package com.lucasdev.jornadacerta.common.data.repository


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private object Keys {
        val DEFAULT_WORKLOAD = stringPreferencesKey("default_workload")
    }

    val defaultWorkloadFlow: Flow<String> = dataStore.data.map { pref ->
        pref[Keys.DEFAULT_WORKLOAD] ?: "08:48"
    }

    suspend fun saveDefaultWorkload(workload: String) {
        dataStore.edit { pref ->
            pref[Keys.DEFAULT_WORKLOAD] = workload
        }
    }

}
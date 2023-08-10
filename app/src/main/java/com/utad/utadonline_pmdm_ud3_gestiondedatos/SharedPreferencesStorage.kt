package com.utad.utadonline_pmdm_ud3_gestiondedatos

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesStorage(private val context: Context) {
    //Nombre de nuestro almacenamiento de datos
    private val storageName = "SharedPreferencesStorage"
    private val sharedPreferencesStorage: SharedPreferences =
        context.getSharedPreferences(storageName, Context.MODE_PRIVATE)


    fun saveString(key: String, value: String) {
        val editor = sharedPreferencesStorage.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun saveBoolean(key: String, value: Boolean) {
        val editor = sharedPreferencesStorage.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun saveInt(key: String, value: Int) {
        val editor = sharedPreferencesStorage.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun saveLong(key: String, value: Long) {
        val editor = sharedPreferencesStorage.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun saveStringSet(key: String, value: MutableSet<String>) {
        val editor = sharedPreferencesStorage.edit()
        editor.putStringSet(key, value)
        editor.apply()
    }

    fun saveFloat(key: String, value: Float) {
        val editor = sharedPreferencesStorage.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    fun deleteValue(key: String) {
        val editor = sharedPreferencesStorage.edit()
        editor.remove(key)
        editor.apply()
    }

    fun removeAllData(key: String) {
        val editor = sharedPreferencesStorage.edit()
        editor.clear()
        editor.apply()
    }

    fun readString(key: String): String? {
        return sharedPreferencesStorage.getString(key, null)
    }

    fun readBoolean(key: String): Boolean? {
        return sharedPreferencesStorage.getBoolean(key, false)
    }

    fun readInt(key: String): Int? {
        return sharedPreferencesStorage.getInt(key, 0)
    }

    fun readFloat(key: String): Float? {
        return sharedPreferencesStorage.getFloat(key, 0f)
    }

    fun readLong(key: String): Long? {
        return sharedPreferencesStorage.getLong(key, 0L)
    }

    fun readStringSet(key: String): Set<String>? {
        return sharedPreferencesStorage.getStringSet(key, null)
    }
}
package com.utad.utadonline_pmdm_ud3_gestiondedatos

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.android.material.snackbar.Snackbar
import com.utad.utadonline_pmdm_ud3_gestiondedatos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding
    private val binding: ActivityMainBinding get() = _binding

    //Nombre de nuestro almacenamiento de datos
    private val storageName = "SharedPreferencesStorage"
    private val storageNameForEncrypted = "SharedPreferencesStorage"
    private lateinit var sharedPreferencesStorage: SharedPreferences
    private lateinit var encryptedSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initEncryptedSharedPreferences()
        initSharedPreferences()
        saveDataExample()
        readData()
    }

    private fun initSharedPreferences() {
        //Obtenemos la instancia de SharedPreferences
        sharedPreferencesStorage = getSharedPreferences(storageName, Context.MODE_PRIVATE)
    }

    private fun initEncryptedSharedPreferences() {
        //Creamos la MaserKey para encriptar y desencriptar nuestros datos
        val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        //Obtenemos la instancia de EncryptedSharedPreferences
        encryptedSharedPreferences = EncryptedSharedPreferences.create(
            storageNameForEncrypted,
            masterKey,
            this,
            //Esquema de encriptado
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            //Esquema de desencriptado
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    private fun saveDataExample() {
        //Ponemos en modo "edición" las sharedPreferences
        val editor = sharedPreferencesStorage.edit()

        //Preparamos los datos para ser guardados
        editor.putString("playerName", "María del Mar")
        editor.putBoolean("aceptedTermsAndConditions", true)
        editor.putInt("age", 29)
        editor.putFloat("score", 2.1f)
        editor.putLong("rankingGlobalPosition", 173324234L)
        editor.putStringSet("playedPositions", mutableSetOf("Interior izquierda", "Central"))

        //Tenemos dos opciones a la hora de guardar los cambios
        editor.apply()// No retorna nada. Es más rápido.
        editor.commit()// Devuelve true si los cambios se guardaron con éxito, false si hubo fallo. Es más lento pero sabemos si hubo un error.
    }



    private fun readData() {
        val name = sharedPreferencesStorage.getString("playerName", null)
        val acceptedTerms = sharedPreferencesStorage.getBoolean("aceptedTermsAndConditions", false)
        val age = sharedPreferencesStorage.getInt("age", 0)
        val score = sharedPreferencesStorage.getFloat("score", 0.0f)
        val rakingPosotion = sharedPreferencesStorage.getLong("rankingGlobalPosition", 0L)
        val playedPositions = sharedPreferencesStorage.getStringSet("playedPositions", null)

        val playerDescription =
            "$name, juega de: $playedPositions, tiene $age años, con una puntuación media de $score goles por partido."
        binding.tvPlayerDescription.text = playerDescription
    }


}
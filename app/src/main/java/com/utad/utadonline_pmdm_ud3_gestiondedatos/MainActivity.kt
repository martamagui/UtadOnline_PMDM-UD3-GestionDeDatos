package com.utad.utadonline_pmdm_ud3_gestiondedatos

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class MainActivity : AppCompatActivity() {

    //Nombre de nuestro almacenamiento de datos
    private val storageName = "SharedPreferencesStorage"
    private val storageNameForEncrypted = "SharedPreferencesStorage"
    private lateinit var sharedPreferencesStorage: SharedPreferences
    private lateinit var encryptedSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initEncryptedSharedPreferences()
        initSharedPreferences()
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
}
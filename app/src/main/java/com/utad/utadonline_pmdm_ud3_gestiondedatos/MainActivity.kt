package com.utad.utadonline_pmdm_ud3_gestiondedatos

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    //Nombre de nuestro almacenamiento de datos
    private val storageName = "SharedPreferencesStorage"
    private lateinit var sharedPreferencesStorage: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Obtenemos la instancia de SharedPreferences
        sharedPreferencesStorage = getSharedPreferences(storageName, Context.MODE_PRIVATE)
    }
}
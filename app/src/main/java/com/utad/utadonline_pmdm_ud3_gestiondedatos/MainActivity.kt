package com.utad.utadonline_pmdm_ud3_gestiondedatos

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.utad.utadonline_pmdm_ud3_gestiondedatos.databinding.ActivityMainBinding
import com.utad.utadonline_pmdm_ud3_gestiondedatos.shared_preferences.BasicSharedPreferencesActivity
import com.utad.utadonline_pmdm_ud3_gestiondedatos.shared_preferences.CleanedSharedPreferencesActivity

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding
    private val binding: ActivityMainBinding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSharedPreferences.setOnClickListener {
            navigateToSharedPreferences()
        }
        binding.btnSharedPreferencesCleaned.setOnClickListener {
            navigateToSharedPreferencesCleanedCode()
        }
    }

    private fun navigateToSharedPreferencesCleanedCode() {
        val intent = Intent(this, CleanedSharedPreferencesActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToSharedPreferences() {
        val intent = Intent(this, BasicSharedPreferencesActivity::class.java)
        startActivity(intent)
    }
}
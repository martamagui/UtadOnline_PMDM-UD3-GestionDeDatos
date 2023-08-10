package com.utad.utadonline_pmdm_ud3_gestiondedatos.shared_preferences

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.utad.utadonline_pmdm_ud3_gestiondedatos.R
import com.utad.utadonline_pmdm_ud3_gestiondedatos.databinding.ActivityCleanedSharedPreferencesBinding

class CleanedSharedPreferencesActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityCleanedSharedPreferencesBinding
    private val binding: ActivityCleanedSharedPreferencesBinding get() = _binding

    private lateinit var myCustomSharedPreferences: MySharedPreferencesStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCleanedSharedPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myCustomSharedPreferences = MySharedPreferencesStorage(this)
        saveDataExample()
        readData()
    }

    private fun saveDataExample() {
        myCustomSharedPreferences.saveDataExample(
            name = "María del Mar", acceptedTerms = true,
            age = 29, score = 2.1f, ranking = 173324234L,
            playedPositions = mutableSetOf("Interior izquierda", "Central")
        )
    }


    private fun readData() {
        binding.tvPlayerDescription.text = myCustomSharedPreferences.readData()
    }


    private fun deleteAllData() {
        myCustomSharedPreferences.deleteAllData()
        myCustomSharedPreferences.deleteOneValue("playerName")
    }


}
package com.utad.utadonline_pmdm_ud3_gestiondedatos.shared_preferences

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.utad.utadonline_pmdm_ud3_gestiondedatos.R
import com.utad.utadonline_pmdm_ud3_gestiondedatos.databinding.ActivityCleanedSharedPreferencesBinding
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        lifecycleScope.launch(Dispatchers.IO) {
            val playerDescription = myCustomSharedPreferences.readData()
            withContext(Dispatchers.Main){
                binding.tvPlayerDescription.text = playerDescription
            }
        }
    }


    private fun deleteAllData() {
        lifecycleScope.launch {
            myCustomSharedPreferences.deleteAllData()
            myCustomSharedPreferences.deleteOneValue("playerName")
        }

    }


}
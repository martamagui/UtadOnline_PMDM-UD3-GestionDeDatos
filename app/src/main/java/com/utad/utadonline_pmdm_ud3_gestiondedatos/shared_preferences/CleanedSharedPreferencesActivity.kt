package com.utad.utadonline_pmdm_ud3_gestiondedatos.shared_preferences

import android.os.Bundle
import android.widget.Toast
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

    //Para guardar la lista de posiciones de los jugadores
    private var playedPositions = mutableSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCleanedSharedPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myCustomSharedPreferences = MySharedPreferencesStorage(this)
        readData()

        binding.btnAdd.setOnClickListener {
            saveDataExample()
        }

        binding.tfPreferencesPlayedPlayedPosition.setEndIconOnClickListener {
            if (isPositionValid()) {
                addPositionToList()
            }
        }
    }

    private fun saveDataExample() {
        val name = binding.etPreferencesPlayerName.text.toString().trim()
        val age: Int = binding.etPreferencesAge.text.toString().trim().toInt()
        val score: Float = binding.etPreferencesScore.text.toString().trim().toFloat()
        val ranking: Long = binding.etPreferencesRanking.text.toString().trim().toLong()
        val acceptedTerms: Boolean = binding.swPreferencesTerms.isChecked

        if (name.isNullOrEmpty() == false && age != null && score != null && ranking != null && acceptedTerms != null && playedPositions.isNotEmpty()) {

            myCustomSharedPreferences.saveDataExample(
                name = name, acceptedTerms = acceptedTerms,
                age = age, score = score, ranking = ranking,
                playedPositions = playedPositions.toSet()
            )
        } else {
            Toast.makeText(this, getString(R.string.add_error_text), Toast.LENGTH_SHORT).show()
        }
        readData()
    }


    private fun readData() {
        //Lanzamos la Coroutine y elejimos en qué Dispatcher queremos que se ejecute
        lifecycleScope.launch(Dispatchers.IO) {
            val playerDescription = myCustomSharedPreferences.readData()
            //Cómo no podemos realizar acciones que impliquen a la interfaz en segundo plano
            //Ejecutamos esta parte del código en el hilo principal.
            withContext(Dispatchers.Main) {
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

    private fun addPositionToList() {
        val position = binding.etPreferencesPlayedPlayedPosition.text.toString().trim()
        playedPositions.add(position)
        //Concetenamos todos los elementos de la lista separados por comas
        val concatenatedString = playedPositions.joinToString(separator = ", ")
        binding.tvPreferencesCurrentPositions.text =
            getString(R.string.item_played_position_title, concatenatedString)
        binding.etPreferencesPlayedPlayedPosition.setText("")
    }

    private fun isPositionValid(): Boolean {
        val position: String = binding.etPreferencesPlayedPlayedPosition.text.toString()
        return position.isNullOrEmpty() == false
    }


}
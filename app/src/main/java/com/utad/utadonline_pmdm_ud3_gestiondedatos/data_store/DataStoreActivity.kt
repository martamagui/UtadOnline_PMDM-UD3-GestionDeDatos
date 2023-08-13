package com.utad.utadonline_pmdm_ud3_gestiondedatos.data_store

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.utad.utadonline_pmdm_ud3_gestiondedatos.R
import com.utad.utadonline_pmdm_ud3_gestiondedatos.databinding.ActivityDataStoreBinding
import com.utad.utadonline_pmdm_ud3_gestiondedatos.shared_preferences.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "MY_DATA_STORE")

class DataStoreActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityDataStoreBinding
    private val binding: ActivityDataStoreBinding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDataStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch(Dispatchers.IO) {
            saveDataInDataStore("Juan Carlos", true, 24, 2.0f, 1231244L, setOf("Portero"))
        }

        lifecycleScope.launch(Dispatchers.IO){
            readDataStorage().collect { response ->
                //Si la respuesta no es nula, mostraremos el texto
                if (response != null) {
                    //Para cambiar la UI debemos accedar al hilo principal
                    withContext(Dispatchers.Main) {
                        binding.tvPlayerDescription.text = response
                    }
                }
            }
        }

    }

    private fun deleteDataStorage(){
        // Si estuvieramos en un Fragmento llamaríamos al contexto así:
        // val context: Context = requireContext()
        val context: Context = this
        //Cuando accedamos a DataStore deberemos hacerlo en una corrutina
        lifecycleScope.launch(Dispatchers.IO) {
            context.dataStore.edit { editor ->
                //Borrar datos individuales
                editor.remove(stringPreferencesKey("name"))
                editor.remove(booleanPreferencesKey("acceptedTerms"))

                //Eliminar TODOS los datos
                editor.clear()
            }
        }
    }

    private fun readDataStorage(): Flow<String> {
        val context: Context = this
        return context.dataStore.data.map { editor ->
            val name = editor[stringPreferencesKey("name")]
            val acceptedTerms = editor[booleanPreferencesKey("acceptedTerms")]
            val age = editor[intPreferencesKey("age")]
            val score = editor[floatPreferencesKey("score")]
            val rakingPosition = editor[longPreferencesKey("ranking")]
            val playedPositions = editor[stringSetPreferencesKey("playedPositions")]

            //Retorna el ultimo valor escrito
            "$name, juega de: $playedPositions, tiene $age años, con una puntuación media de $score goles por partido."
        }
    }

    private suspend fun saveDataInDataStore(
        name: String, acceptedTerms: Boolean, age: Int,
        score: Float, ranking: Long, playedPositions: Set<String>
    ) {
        // Si estuvieramos en un Fragmento llamaríamos al contexto así:
        // val context: Context = requireContext()
        val context: Context = this
        //Cuando accedamos a DataStore deberemos hacerlo en una corrutina
        context.dataStore.edit { editor ->
            editor[stringPreferencesKey("name")] = name
            editor[booleanPreferencesKey("acceptedTerms")] = acceptedTerms
            editor[intPreferencesKey("age")] = age
            editor[floatPreferencesKey("score")] = score
            editor[longPreferencesKey("ranking")] = ranking
            editor[stringSetPreferencesKey("playedPositions")] = playedPositions
        }
    }




}












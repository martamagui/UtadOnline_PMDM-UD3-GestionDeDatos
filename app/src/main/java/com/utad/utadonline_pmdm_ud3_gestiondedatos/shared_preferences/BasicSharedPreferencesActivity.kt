package com.utad.utadonline_pmdm_ud3_gestiondedatos.shared_preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.utad.utadonline_pmdm_ud3_gestiondedatos.R
import com.utad.utadonline_pmdm_ud3_gestiondedatos.databinding.ActivityBasicSharedPreferencesBinding

class BasicSharedPreferencesActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityBasicSharedPreferencesBinding
    private val binding: ActivityBasicSharedPreferencesBinding get() = _binding

    //Nombre de nuestro almacenamiento de datos
    private val storageName = "SharedPreferencesStorage"
    private val storageNameForEncrypted = "SharedPreferencesStorage"
    private lateinit var sharedPreferencesStorage: SharedPreferences
    private lateinit var encryptedSharedPreferences: SharedPreferences

    //Para guardar la lista de posiciones de los jugadores
    private var playedPositions = mutableSetOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBasicSharedPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initEncryptedSharedPreferences()
        initSharedPreferences()
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
        val name = binding.etPreferencesPlayerName.text.toString().trim()
        val age: Int = binding.etPreferencesAge.text.toString().trim().toInt()
        val score: Float = binding.etPreferencesScore.text.toString().trim().toFloat()
        val ranking: Long = binding.etPreferencesRanking.text.toString().trim().toLong()
        val acceptedTerms: Boolean = binding.swPreferencesTerms.isChecked

        if (name.isNullOrEmpty() == false && age != null && score != null && ranking != null && acceptedTerms != null && playedPositions.isNotEmpty()) {
            //Ponemos en modo "edición" las sharedPreferences
            val editor = sharedPreferencesStorage.edit()

            //Preparamos los datos para ser guardados
            editor.putString("playerName", name)
            editor.putBoolean("acceptedTermsAndConditions", acceptedTerms)
            editor.putInt("age", age)
            editor.putFloat("score", score)
            editor.putLong("rankingGlobalPosition", ranking)
            editor.putStringSet("playedPositions", playedPositions.toSet())

            //Tenemos dos opciones a la hora de guardar los cambios
            editor.apply()// No retorna nada. Es más rápido.
            editor.commit()// Devuelve true si los cambios se guardaron con éxito, false si hubo fallo. Es más lento pero sabemos si hubo un error.
        } else {
            Toast.makeText(this, getString(R.string.add_error_text), Toast.LENGTH_SHORT).show()
        }
        readData()
    }


    private fun readData() {
        val name = sharedPreferencesStorage.getString("playerName", "---")
        val acceptedTerms = sharedPreferencesStorage.getBoolean("acceptedTermsAndConditions", false)
        val age = sharedPreferencesStorage.getInt("age", 0)
        val score = sharedPreferencesStorage.getFloat("score", 0.0f)
        val rakingPosition = sharedPreferencesStorage.getLong("rankingGlobalPosition", 0L)
        val playedPositions = sharedPreferencesStorage.getStringSet("playedPositions", setOf("---"))

        val playerDescription =
            "El jugador guardado previamente se llama $name, juega de: ${playedPositions?.joinToString(",")}, tiene $age años, con una puntuación media de $score goles por partido."
        binding.tvPlayerDescription.text = playerDescription
    }

    private fun deleteAllData() {
        //Ponemos en modo "edición" las sharedPreferences
        val editor = sharedPreferencesStorage.edit()

        //Borra el dato guardado bajo la clave que enviemos por parámetro
        editor.remove("playerName")

        //Borra TODOS los datos guardados
        editor.clear()

        //Para que esto se aplique una vez terminemos, debemos hacer
        // ".commit()" o ".apply()" igual que al guardar datos
        editor.apply()
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
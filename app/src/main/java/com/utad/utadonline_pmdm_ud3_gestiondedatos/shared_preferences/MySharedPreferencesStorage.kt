package com.utad.utadonline_pmdm_ud3_gestiondedatos.shared_preferences

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.delay

class MySharedPreferencesStorage(private val context: Context) {
    //Nombre de nuestro almacenamiento de datos
    private val storageName = "SharedPreferencesStorage"

    //Obtenemos la instancia de SharedPreferences
    private val sharedPreferencesStorage: SharedPreferences =
        context.getSharedPreferences(storageName, Context.MODE_PRIVATE)


    fun saveDataExample(
        name: String, acceptedTerms: Boolean, age: Int,
        score: Float, ranking: Long, playedPositions: Set<String>
    ) {
        //Ponemos en modo "edición" las sharedPreferences
        val editor = sharedPreferencesStorage.edit()

        //Preparamos los datos para ser guardados
        editor.putString("playerName", name)
        editor.putBoolean("acceptedTermsAndConditions", acceptedTerms)
        editor.putInt("age", age)
        editor.putFloat("score", score)
        editor.putLong("rankingGlobalPosition", ranking)
        editor.putStringSet("playedPositions", playedPositions)

        //Tenemos dos opciones a la hora de guardar los cambios
        editor.apply()// No retorna nada. Es más rápido.
        //editor.commit()// Devuelve true si los cambios se guardaron con éxito, false si hubo fallo. Es más lento pero sabemos si hubo un error.
    }

    suspend fun readData(): String {
        val name = sharedPreferencesStorage.getString("playerName", null)
        val acceptedTerms = sharedPreferencesStorage.getBoolean("acceptedTermsAndConditions", false)
        val age = sharedPreferencesStorage.getInt("age", 0)
        val score = sharedPreferencesStorage.getFloat("score", 0.0f)
        val rakingPosition = sharedPreferencesStorage.getLong("rankingGlobalPosition", 0L)
        val playedPositions = sharedPreferencesStorage.getStringSet("playedPositions", null)
        delay(500)
        return "$name, juega de: $playedPositions, tiene $age años, con una puntuación media de $score goles por partido."
    }

    suspend fun readPlayer(): Player {
        val name = sharedPreferencesStorage.getString("playerName", null)
        val acceptedTerms = sharedPreferencesStorage.getBoolean("acceptedTermsAndConditions", false)
        val age = sharedPreferencesStorage.getInt("age", 0)
        val score = sharedPreferencesStorage.getFloat("score", 0.0f)
        val rakingPosition = sharedPreferencesStorage.getLong("rankingGlobalPosition", 0L)
        val playedPositions = sharedPreferencesStorage.getStringSet("playedPositions", null)

        return Player(
            name = name,
            acceptedTerms = acceptedTerms,
            age = age,
            score = score,
            ranking = rakingPosition,
            playedPositions = playedPositions
        )
    }

    suspend fun deleteAllData() {
        //Ponemos en modo "edición" las sharedPreferences
        val editor = sharedPreferencesStorage.edit()

        //Borra TODOS los datos guardados
        editor.clear()

        //Para que esto se aplique una vez terminemos, debemos hacer
        // ".commit()" o ".apply()" igual que al guardar datos
        editor.apply()
    }

    suspend fun deleteOneValue(key: String) {
        //Ponemos en modo "edición" las sharedPreferences
        val editor = sharedPreferencesStorage.edit()

        //Borra el dato guardado bajo la clave que en viemos por parámetro
        editor.remove(key)
        //Para que esto se aplique una vez terminemos, debemos hacer
        // ".commit()" o ".apply()" igual que al guardar datos
        editor.apply()
    }


}


package com.utad.utadonline_pmdm_ud3_gestiondedatos.paper_db

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.utad.utadonline_pmdm_ud3_gestiondedatos.R
import com.utad.utadonline_pmdm_ud3_gestiondedatos.databinding.ActivityPaperDbaddNewPlayerBinding
import com.utad.utadonline_pmdm_ud3_gestiondedatos.shared_preferences.Player
import io.paperdb.Paper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaperDBAddNewPlayerActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityPaperDbaddNewPlayerBinding
    private val binding: ActivityPaperDbaddNewPlayerBinding get() = _binding
    private var playedPositions = mutableSetOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPaperDbaddNewPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            if (dataIsOkay()) {
                //Si todo está en orden, guardamos los datos escritos
                val name = binding.etName.text.toString()
                val age = binding.etAge.text.toString().toInt()
                val player =
                    Player(name = name, true, age, 1.0f, 1L, playedPositions = playedPositions)
                saveData(player)
                //Tras guardar, salimos del formulario
                finish()
            } else {
                showError()
            }
        }

        binding.tfPlayedPlayedPosition.setEndIconOnClickListener {
            if (isPositionValid()) {
                addPositionToList()
            }
        }

    }

    private fun saveData(player: Player) {
        lifecycleScope.launch(Dispatchers.IO) {
            //Guardamos nuestro jugador en el book "players"
            //Poniendo como clave su nombre
            Paper.book("players").write(player.name!!, player)
        }
    }

    private fun addPositionToList() {
        val position = binding.etPlayedPlayedPosition.text.toString().trim()
        playedPositions.add(position)
        //Concetenamos todos los elementos de la lista separados por comas
        val concatenatedString = playedPositions.joinToString(separator = ", ")
        binding.tvCurrentPositions.text =
            getString(R.string.item_played_position_title, concatenatedString)
        binding.etPlayedPlayedPosition.setText("")
    }

    private fun isPositionValid(): Boolean {
        val position: String = binding.etPlayedPlayedPosition.text.toString()
        return position.isNullOrEmpty() == false
    }

    private fun showError() {
        val message = getString(R.string.add_error_text)
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun dataIsOkay(): Boolean {
        val name = binding.etName.text
        val age = binding.etAge.text
        return name.isNullOrEmpty() == false && age.isNullOrEmpty() == false && playedPositions.size > 0
    }
}
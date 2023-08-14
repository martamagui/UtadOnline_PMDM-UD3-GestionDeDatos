package com.utad.utadonline_pmdm_ud3_gestiondedatos.paper_db

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.utad.utadonline_pmdm_ud3_gestiondedatos.R
import com.utad.utadonline_pmdm_ud3_gestiondedatos.databinding.ActivityPaperDbaddNewPlayerBinding

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
                saveData()
            } else {
                showError()
            }
        }
        binding.tfPlayedPlayedPosition.setEndIconOnClickListener {
            if(isPositionValid()){
                addPositionToList()
            }
        }
    }

    private fun addPositionToList() {
        val position = binding.etPlayedPlayedPosition.text.toString().trim()
        playedPositions.add(position)
        val concatenatedString = playedPositions.joinToString(separator = ", ")
        binding.tvCurrentPositions.text =  concatenatedString
    }

    private fun isPositionValid(): Boolean {
        val position: String = binding.etPlayedPlayedPosition.text.toString()
        return position.isNullOrEmpty() == false
    }

    private fun showError() {
        val message = getString(R.string.add_error_text)
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun saveData() {

    }

    private fun dataIsOkay(): Boolean {
        val name = binding.etName.text
        val age = binding.etAge.text
        return name.isNullOrEmpty() == false && age.isNullOrEmpty() == false && playedPositions.size > 0
    }
}
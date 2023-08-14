package com.utad.utadonline_pmdm_ud3_gestiondedatos.paper_db

import android.content.Intent
import android.icu.lang.UCharacter.VerticalOrientation
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Orientation
import com.utad.utadonline_pmdm_ud3_gestiondedatos.DataBasePlayerAdapter
import com.utad.utadonline_pmdm_ud3_gestiondedatos.R
import com.utad.utadonline_pmdm_ud3_gestiondedatos.databinding.ActivityPaperDbactivityBinding
import com.utad.utadonline_pmdm_ud3_gestiondedatos.shared_preferences.Player

class PaperDBActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityPaperDbactivityBinding
    private val binding: ActivityPaperDbactivityBinding get() = _binding
    private val adapter: DataBasePlayerAdapter = DataBasePlayerAdapter { deleteItem(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPaperDbactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUI()
    }

    private fun setUI() {
        binding.rvPlayer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        binding.rvPlayer.adapter = adapter

        binding.fabAddNewPlayer.setOnClickListener { goToAddPlayer() }
    }

    private fun deleteItem(it: Player) {

    }

    private fun goToAddPlayer() {
        val intent = Intent(this, PaperDBAddNewPlayerActivity::class.java)
        startActivity(intent)
    }
}
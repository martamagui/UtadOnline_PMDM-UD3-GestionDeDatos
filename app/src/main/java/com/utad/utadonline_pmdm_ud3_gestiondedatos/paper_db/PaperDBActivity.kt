package com.utad.utadonline_pmdm_ud3_gestiondedatos.paper_db

import android.content.Intent
import android.icu.lang.UCharacter.VerticalOrientation
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Orientation
import com.utad.utadonline_pmdm_ud3_gestiondedatos.DataBasePlayerAdapter
import com.utad.utadonline_pmdm_ud3_gestiondedatos.R
import com.utad.utadonline_pmdm_ud3_gestiondedatos.databinding.ActivityPaperDbactivityBinding
import com.utad.utadonline_pmdm_ud3_gestiondedatos.shared_preferences.Player
import io.paperdb.Paper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    override fun onResume() {
        super.onResume()
        readPlayersInDataBase()
    }

    private fun readPlayersInDataBase() {
        //Lanzamos una Coroutine para leer los datos
        lifecycleScope.launch(Dispatchers.IO) {
            val keyList: List<String> = Paper.book("players").allKeys
            val playerList: MutableList<Player> = mutableListOf()
            //Por cada key del book "players", buscamos el jugador y lo añadimos a la lista
            keyList.forEach { key ->
                val player: Player? = Paper.book("players").read<Player>(key)
                if (player != null) {
                    playerList.add(player)
                }
            }
            //Una vez tenemos la lista de jugadores, le pasamos los datos a la RecyclerView
            //Esto deberemos hacerlo en el hilo principal
            withContext(Dispatchers.Main) {
                if (adapter != null) {
                    adapter.submitList(playerList)
                }
            }
        }
    }

    private fun setUI() {
        binding.rvPlayer.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
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
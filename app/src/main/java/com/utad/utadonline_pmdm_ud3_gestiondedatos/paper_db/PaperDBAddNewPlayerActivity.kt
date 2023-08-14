package com.utad.utadonline_pmdm_ud3_gestiondedatos.paper_db

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.utad.utadonline_pmdm_ud3_gestiondedatos.R
import com.utad.utadonline_pmdm_ud3_gestiondedatos.databinding.ActivityPaperDbaddNewPlayerBinding

class PaperDBAddNewPlayerActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityPaperDbaddNewPlayerBinding
    private val binding: ActivityPaperDbaddNewPlayerBinding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPaperDbaddNewPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
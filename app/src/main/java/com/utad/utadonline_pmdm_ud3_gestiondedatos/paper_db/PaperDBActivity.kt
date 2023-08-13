package com.utad.utadonline_pmdm_ud3_gestiondedatos.paper_db

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.utad.utadonline_pmdm_ud3_gestiondedatos.R
import com.utad.utadonline_pmdm_ud3_gestiondedatos.databinding.ActivityPaperDbactivityBinding

class PaperDBActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityPaperDbactivityBinding
    private val binding: ActivityPaperDbactivityBinding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPaperDbactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
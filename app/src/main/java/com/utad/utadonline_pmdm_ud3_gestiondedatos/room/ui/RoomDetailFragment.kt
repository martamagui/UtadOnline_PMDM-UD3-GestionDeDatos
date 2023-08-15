package com.utad.utadonline_pmdm_ud3_gestiondedatos.room.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.utad.utadonline_pmdm_ud3_gestiondedatos.databinding.FragmentRoomDetailBinding

class RoomDetailFragment : Fragment() {

    private lateinit var _binding: FragmentRoomDetailBinding
    private val binding: FragmentRoomDetailBinding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRoomDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}
package com.utad.utadonline_pmdm_ud3_gestiondedatos.room.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.utad.utadonline_pmdm_ud3_gestiondedatos.databinding.FragmentRoomListBinding

class RoomListFragment : Fragment() {

    private lateinit var _binding: FragmentRoomListBinding
    private val binding: FragmentRoomListBinding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRoomListBinding.inflate(inflater, container, false)
        return binding.root
    }

}
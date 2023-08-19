package com.utad.utadonline_pmdm_ud3_gestiondedatos.room.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.utad.utadonline_pmdm_ud3_gestiondedatos.databinding.ActivityRoomCreateBinding

class RoomCreateActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityRoomCreateBinding
    private val binding: ActivityRoomCreateBinding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRoomCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkIfWeAlreadyHaveThisPermission()
    }

    private fun checkIfWeAlreadyHaveThisPermission() {
        val externalStoragePermission: String = Manifest.permission.READ_EXTERNAL_STORAGE
        val permissionStatus = ContextCompat.checkSelfPermission(this, externalStoragePermission)

        // Verificar si ya tenemos permiso
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            // Ya tenemos permiso, podemos realizar la acción que lo necesita.
            showGrantedPermissionMessage()
        } else {
            // No tenemos permiso, mostramos la solicitud al usuario.
            requestPermissionLauncher.launch(externalStoragePermission)
        }
    }

    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Snackbar.make(
                    binding.root,
                    "El usuario nos concedió el permiso",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                Snackbar.make(binding.root, "El usuario lo denegó", Snackbar.LENGTH_SHORT).show()
            }
        }

    private fun showGrantedPermissionMessage() {
        Snackbar.make(
            binding.root,
            "El usuario había concedido el permiso previamente",
            Snackbar.LENGTH_SHORT
        ).show()
    }

}

package com.utad.utadonline_pmdm_ud3_gestiondedatos.room.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
        //Mediante Manifest.permission.NuestroPermiso
        // accedemos al permiso que queremos comprobar
        val externalStoragePermission: String = Manifest.permission.READ_EXTERNAL_STORAGE
        //El método ContextCompat.checkSelfPermission() nos devuelve el estado
        //del permiso que pasemos cómo parámetro. Lo guardamos en una variable.
        val permissionStatus = ContextCompat.checkSelfPermission(this, externalStoragePermission)

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            // Ya tenemos permiso, podemos realizar la acción que lo necesita.
            showGrantedPermissionMessage()
        } else {

            // Debemos comprobar si ya hemos pedido con anterioridad el permiso
            val shouldRequestPermission = shouldShowRequestPermissionRationale(externalStoragePermission)
            // Deser así, debemos explicar al usuario porqué es necesario el permiso
            if (shouldRequestPermission) {
                MaterialAlertDialogBuilder(this)
                    .setTitle("El permiso es necesario")
                    .setMessage("Es necesario para poder cargar tu imagen de empleado y completar tu ficha")
                    .setPositiveButton("Okay") { dialog, which ->
                        //Si el usuario acepta, pedimos de nuevo los permisos
                        requestPermissionLauncher.launch(externalStoragePermission)
                    }
                    .setNegativeButton("Denegar") { dialog, which -> finish() }
                    .show()
            } else {
                requestPermissionLauncher.launch(externalStoragePermission)
            }
        }
    }

    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                //El permiso ha sido concedido, podemos realizar la acción que lo necesitaba
                Snackbar.make(binding.root, "El usuario nos concedió el permiso", Snackbar.LENGTH_SHORT).show()
            } else {
                //El permiso ha sido denegado, deberemos restringir el acceso a esta parte de la app
                Snackbar.make(binding.root, "El usuario lo denegó", Snackbar.LENGTH_SHORT).show()
                finish()
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

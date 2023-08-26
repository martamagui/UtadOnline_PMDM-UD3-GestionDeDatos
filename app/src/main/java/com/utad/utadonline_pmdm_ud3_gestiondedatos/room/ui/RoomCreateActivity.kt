package com.utad.utadonline_pmdm_ud3_gestiondedatos.room.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.utad.utadonline_pmdm_ud3_gestiondedatos.MyApplication
import com.utad.utadonline_pmdm_ud3_gestiondedatos.databinding.ActivityRoomCreateBinding
import com.utad.utadonline_pmdm_ud3_gestiondedatos.room.entities.Employee
import com.utad.utadonline_pmdm_ud3_gestiondedatos.room.entities.VacationInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream


class RoomCreateActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityRoomCreateBinding
    private val binding: ActivityRoomCreateBinding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRoomCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkIfWeAlreadyHaveThisPermission()
        binding.btnGallery.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            intent.type = "image/*"
            imageGalleryLauncher.launch(intent)
        }

        binding.btnSave.setOnClickListener {
            if (employeeImage != null) {
                val employee =
                    Employee(0, "Berta", "Contabilidad", VacationInfo(1, 29), employeeImage!!)
                saveEmployee(employee)
            }
        }

    }

    private fun saveEmployee(employee: Employee) {
        val application = applicationContext as MyApplication

        lifecycleScope.launch(Dispatchers.IO) {
            //Guardamos a nuestro empleado cuando haya rellenado todos los datos
            application.room.employeeDao().addNewEmployee(employee)

            // Si queremos recuperar la imagen de la base de datos, obtenemos el empleado
            val savedEmployee = application.room.employeeDao().getEmployeeById(employee.id)
            withContext(Dispatchers.Main) {
                // En el hilo principal usaremos "setImageBitmap()"
                // para mostrar la imagen en la ImageView
                if (savedEmployee != null && savedEmployee.image != null)
                    binding.ivEmployeeImage.setImageBitmap(savedEmployee.image)
            }
        }
    }

    private var employeeImage: Bitmap? = null
    private var imageGalleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    val selectedImageUri: Uri? = data.data
                    employeeImage = convertUriToBitmap(selectedImageUri)
                } else {
                    showErrorMessageNoImage()
                }
            } else {
                showErrorMessageNoImage()
            }
        }

    private fun convertUriToBitmap(uri: Uri?): Bitmap? {
        try {
            //A partir del Uri de la imagen recibida, obtenemos el Bitmap
            val inputStream = contentResolver.openInputStream(uri!!)
            val image = BitmapFactory.decodeStream(inputStream)
            //Si es una imagen pequeña devolveremos el Bitmap
            if (image.byteCount <= 2500000) {
                return image
            } else {
                //En caso de no serlo, comprimiremos la imagen hasta que se pueda guardar en Room
                var compressedImage = image
                do {
                    var scaleWidth = compressedImage.width / 2
                    var scaleHeight = compressedImage.height / 2

                    compressedImage =
                        Bitmap.createScaledBitmap(image, scaleWidth, scaleHeight, true)
                } while (compressedImage.byteCount >= 2500000)
                //Cuando sea lo suficientemente pequeña, devolveremos la imagen comprimida
                return compressedImage
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
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
            val shouldRequestPermission =
                shouldShowRequestPermissionRationale(externalStoragePermission)
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
                Snackbar.make(
                    binding.root,
                    "El usuario nos concedió el permiso",
                    Snackbar.LENGTH_SHORT
                ).show()
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

    private fun showErrorMessageNoImage() {
        Toast.makeText(this, "No has seleccionado ninguna imagen", Toast.LENGTH_SHORT).show()
    }

}

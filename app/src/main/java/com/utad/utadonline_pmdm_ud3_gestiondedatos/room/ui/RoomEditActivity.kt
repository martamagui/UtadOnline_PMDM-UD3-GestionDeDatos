package com.utad.utadonline_pmdm_ud3_gestiondedatos.room.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.utad.utadonline_pmdm_ud3_gestiondedatos.MyApplication
import com.utad.utadonline_pmdm_ud3_gestiondedatos.R
import com.utad.utadonline_pmdm_ud3_gestiondedatos.databinding.ActivityRoomEditBinding
import com.utad.utadonline_pmdm_ud3_gestiondedatos.room.entities.Employee
import com.utad.utadonline_pmdm_ud3_gestiondedatos.room.entities.VacationInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomEditActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityRoomEditBinding
    private val binding: ActivityRoomEditBinding get() = _binding

    private lateinit var currentEmployee: Employee
    private lateinit var application: MyApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRoomEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        application = applicationContext as MyApplication
        val employeeId = intent.extras?.getInt("employeeId")

        if (employeeId != null) {
            lifecycleScope.launch(Dispatchers.IO) {
                getEmployeeData(employeeId)
            }
        } else {
            Toast.makeText(this, "No se ha recibido información del empleado", Toast.LENGTH_SHORT)
                .show()
            finish()
        }
        binding.btnSave.setOnClickListener { saveNewData() }
    }

    override fun onResume() {
        super.onResume()
        //Para que se actualice la información al regresar llamamos de nuevo a la info del empleado en onResume
        val employeeId = intent.extras?.getInt("employeeId")
        if (employeeId != null) {
            lifecycleScope.launch(Dispatchers.IO) {
                getEmployeeData(employeeId)
            }
        }
    }

    private fun saveNewData() {
        val name = binding.etEditEmployeeName.text.toString().trim()
        val job = binding.etEditEmployeeJob.text.toString().trim()
        val daysTaken = binding.etEditDaysTaken.text.toString().trim().toInt()
        val daysLeft = binding.etEditDaysLeft.text.toString().trim().toInt()

        if (name.isNullOrEmpty() == false && job.isNullOrEmpty() == false && daysTaken != null && daysLeft != null) {
            lifecycleScope.launch(Dispatchers.IO) {
                val newEmployee = Employee(
                    id = currentEmployee.id,
                    name = name,
                    job = job,
                    vacationInfo = VacationInfo(daysLeft, daysTaken),
                    image = currentEmployee.image
                )
                //Actualizamos el empleado
                application.room.employeeDao().updateEmployee(newEmployee)
                withContext(Dispatchers.Main) {
                    //regresamos a la pantalla anterior
                    finish()
                }
            }
        } else {
            Toast.makeText(this, "Por favor, rellena toda la información", Toast.LENGTH_SHORT)
                .show()
        }
    }

    //Marrco la función como suspend para que obligar a que se ejecute en una corrutina
    private suspend fun getEmployeeData(employeeId: Int) {
        // Hago la petición de el empleado
        currentEmployee = application.room.employeeDao().getEmployeeById(employeeId)
        //Mostramos los datos del empleado en la interfaz
        withContext(Dispatchers.Main) {
            if (currentEmployee != null) {
                setEmployeeData(currentEmployee)
            } else {
                Toast.makeText(
                    this@RoomEditActivity,
                    "No se ha recibido información del empleado",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setEmployeeData(employee: Employee) {
        binding.etEditEmployeeName.setText(employee.name)
        binding.etEditEmployeeJob.setText(employee.job)
        binding.etEditDaysLeft.setText("${employee.vacationInfo.daysLeft}")
        binding.etEditDaysTaken.setText("${employee.vacationInfo.daysAlreadyTaken}")
        binding.ivEditEmployeeImage.setImageBitmap(employee.image)
    }
}
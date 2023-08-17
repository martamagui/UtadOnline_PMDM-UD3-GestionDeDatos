package com.utad.utadonline_pmdm_ud3_gestiondedatos.room.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.utad.utadonline_pmdm_ud3_gestiondedatos.MyApplication
import com.utad.utadonline_pmdm_ud3_gestiondedatos.R
import com.utad.utadonline_pmdm_ud3_gestiondedatos.room.entities.ClockIn
import com.utad.utadonline_pmdm_ud3_gestiondedatos.room.entities.Employee
import com.utad.utadonline_pmdm_ud3_gestiondedatos.room.entities.VacationInfo
import kotlinx.coroutines.launch

class RoomSampleActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_sample)

        saveEmployee()
    }

    private fun saveEmployee() {
        val app = applicationContext as MyApplication

        lifecycleScope.launch {
            //Añadir un nuevo empleado
            app.room.employeeDao().addNewEmployee(
                Employee(
                    id = 0,
                    name = "Julia",
                    job = "Marketing",
                    vacationInfo = VacationInfo(21, 3)
                )
            )
            app.room.employeeDao().getAllEmployees().forEach { employee: Employee ->
                Log.d("Employee", "$employee")
            }
            //Añadir una nueva hora de entrada
            app.room.clockInDao().addClockIn(clockIn = ClockIn(0,"17/08/2023 11:00", 1))

            app.room.employeeDao().getEmployeeClockIn(1).forEach { clockin ->
                Log.d("ClockIn", "$clockin")
            }
        }

    }
}
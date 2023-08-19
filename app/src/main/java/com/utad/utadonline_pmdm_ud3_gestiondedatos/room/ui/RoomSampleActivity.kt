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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomSampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_sample)

        //Accedemos a MyApplication para poder acceder a room
        val app = applicationContext as MyApplication

        saveEmployee(app)
        addClockIn(app)
        readEmployees(app)
        readClockInAndEmployees(app)

    }

    private fun readClockInAndEmployees(app: MyApplication) {
        lifecycleScope.launch(Dispatchers.IO) {
            //Leer la query combinando la tabla de ClockIn y la de Employee
            app.room.employeeDao().getEmployeeClockIn(1).forEach { clockin ->
                Log.d("ClockIn", "$clockin")
            }

            //Leer los employees con los checkIns mediante la relación de ambas tablas
            app.room.employeeDao().getEmployeesAndCheckIns().forEach { relation ->
                Log.d("Relation", "$relation")
            }
        }
    }

    private fun readEmployees(app: MyApplication) {
        lifecycleScope.launch(Dispatchers.IO) {
            //Leer todos los empleados
            app.room.employeeDao().getAllEmployees().forEach { employee: Employee ->
                Log.d("Employee", "$employee")
            }
        }
    }

    private fun addClockIn(app: MyApplication) {
        lifecycleScope.launch(Dispatchers.IO) {
            //Añadir una nueva hora de entrada
            app.room.clockInDao().addClockIn(clockIn = ClockIn(0, "17/08/2023 11:00", 1))
        }
    }

    private fun saveEmployee(app: MyApplication) {
        //Cómo añadir un nuevo empleado.
        lifecycleScope.launch(Dispatchers.IO) {
            // Si a la Primary key le hemos puesto que se autogenere,
            // pondremos como valor en ella 0
            app.room.employeeDao().addNewEmployee(
                Employee(
                    id = 0,
                    name = "Julia",
                    job = "Marketing",
                    vacationInfo = VacationInfo(21, 3)
                )
            )
        }
    }

}
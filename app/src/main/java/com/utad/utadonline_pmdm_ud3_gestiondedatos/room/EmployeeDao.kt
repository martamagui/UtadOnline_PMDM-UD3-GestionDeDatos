package com.utad.utadonline_pmdm_ud3_gestiondedatos.room

import android.security.identity.AccessControlProfileId
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.utad.utadonline_pmdm_ud3_gestiondedatos.room.entities.*

@Dao
interface EmployeeDao {

    //Agrega a la tabla un único empleado
    @Insert
    suspend fun addNewEmployee(employee: Employee)

    //Agrega a la tabla ambos empleados
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTwoNewEmployees(employeeOne: Employee, employeeTwo: Employee)

    //Agrega a la tabla todos los empleados de la lista
    @Insert
    suspend fun addManyNewEmployees(employeeList: List<Employee>)


    //Actualizar un único empleado
    @Update
    suspend fun updateEmployee(employee: Employee)

    //Actualizar varios empleados al tiempo
    @Update
    suspend fun updateManyEmployees(employeeList: List<Employee>)


    //Eliminar un único empleado
    @Delete
    suspend fun deleteEmployee(employee: Employee)

    //Eliminar varios empleados al tiempo
    @Delete
    suspend fun deleteManyEmployees(employeeList: List<Employee>)


    //Devuelve todos los empleados
    @Query("SELECT * FROM employee")
    suspend fun getAllEmployees(): List<Employee>

    //Devuelve todos los empleados que su trabajo coincida por el pasado por parámetro
    @Query("SELECT * FROM employee WHERE job=:jobParam")
    suspend fun getEmployeeByJob(jobParam: String): List<Employee>

    //Elimina todos los empleados que su trabajo coincida por el pasado por parámetro
    @Query("DELETE FROM employee WHERE job=:jobParam")
    suspend fun deleteEmployeesByJob(jobParam: String)


    @Query(
        "SELECT time AS clockInTime, employee.id AS employeeId FROM clockin, " +
                "employee WHERE employee.id = clockin.employeeId AND employee.id = :employeeIdParam"
    )
    suspend fun getEmployeeClockIn(employeeIdParam: Int): List<EmployeeClockIn>

    //Obtener todos los resultados de la realación Employee-ClockIn mediante la
    // Data Class que las relaciona


    @Transaction
    @Query("SELECT *  FROM Employee")
    fun getEmployeesAndCheckIns(): List<EmployeeClockInRelation>





}





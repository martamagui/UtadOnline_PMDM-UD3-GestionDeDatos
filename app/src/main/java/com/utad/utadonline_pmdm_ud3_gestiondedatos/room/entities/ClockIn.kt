package com.utad.utadonline_pmdm_ud3_gestiondedatos.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ClockIn(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val time: String,
    val employeeId: Int
)


data class EmployeeClockIn(
    val employeeId: Int,
    val clockInTime: String
)



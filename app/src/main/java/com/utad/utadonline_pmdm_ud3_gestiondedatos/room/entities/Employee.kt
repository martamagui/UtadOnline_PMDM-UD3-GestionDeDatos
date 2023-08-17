package com.utad.utadonline_pmdm_ud3_gestiondedatos.room.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Employee(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val job: String,
    @Embedded
    val vacationInfo: VacationInfo,
)

data class VacationInfo(
    val daysLeft: Int,
    val daysAlreadyTaken: Int
)





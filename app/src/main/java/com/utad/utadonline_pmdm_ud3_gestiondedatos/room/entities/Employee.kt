package com.utad.utadonline_pmdm_ud3_gestiondedatos.room.entities

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Employee(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    @ColumnInfo(name = "job", defaultValue = "Programador")
    val job: String,
    @Embedded
    val vacationInfo: VacationInfo,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val image: Bitmap
)

data class VacationInfo(
    val daysLeft: Int,
    val daysAlreadyTaken: Int
)





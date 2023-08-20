package com.utad.utadonline_pmdm_ud3_gestiondedatos.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.utad.utadonline_pmdm_ud3_gestiondedatos.room.entities.ClockIn
import com.utad.utadonline_pmdm_ud3_gestiondedatos.room.entities.Employee

@Database(entities = [Employee::class, ClockIn::class], version = 1)
@TypeConverters(ImageTypeConverters::class)
abstract class MyDataBase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
    abstract fun clockInDao(): ClockInDao
}



package com.utad.utadonline_pmdm_ud3_gestiondedatos.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.utad.utadonline_pmdm_ud3_gestiondedatos.room.entities.Employee

@Database(entities = [Employee::class], version = 1)
abstract class MyDataBase : RoomDatabase() {
}



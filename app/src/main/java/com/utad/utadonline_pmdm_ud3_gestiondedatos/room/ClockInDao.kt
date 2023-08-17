package com.utad.utadonline_pmdm_ud3_gestiondedatos.room

import androidx.room.Dao
import androidx.room.Insert
import com.utad.utadonline_pmdm_ud3_gestiondedatos.room.entities.ClockIn

@Dao
interface ClockInDao {
    @Insert
    suspend fun addClockIn(clockIn: ClockIn)
}
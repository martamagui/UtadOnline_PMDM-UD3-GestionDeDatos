package com.utad.utadonline_pmdm_ud3_gestiondedatos

import android.app.Application
import io.paperdb.Paper

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Inicializamos PaperDB
        // La inicialización debe ser llamada desde el hilo principal
        Paper.init(applicationContext)
    }

}
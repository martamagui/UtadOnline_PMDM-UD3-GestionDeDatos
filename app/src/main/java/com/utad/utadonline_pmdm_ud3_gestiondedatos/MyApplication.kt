package com.utad.utadonline_pmdm_ud3_gestiondedatos

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.utad.utadonline_pmdm_ud3_gestiondedatos.room.MyDataBase
import io.paperdb.Paper

//Recordad que para que esta clase sea usada. Deberéis añadirla en el Manifest
// dentro de la tag de la aplicación con el atributo -> android:name="ruta de esta clase"

class MyApplication : Application() {
    //Instanciación de Room
   lateinit var room : MyDataBase

    override fun onCreate() {
        super.onCreate()
        room = Room.databaseBuilder(
            applicationContext,
            MyDataBase::class.java,
            "MyDataBase"
        ).build()
    }

}



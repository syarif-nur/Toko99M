package com.tdpc.toko99.core.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tdpc.toko99.core.data.local.entity.BarangEntity


@Database(entities = [BarangEntity::class], version = 1, exportSchema = false)
abstract class BarangDatabase: RoomDatabase() {

    abstract fun barangDao(): BarangDao

    companion object {
        @Volatile
        private var INSTANCE: BarangDatabase? = null

        fun getInstance(context: Context): BarangDatabase =
            INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BarangDatabase::class.java,
                    "Meal.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
    }

}
package com.ssu.soongsilhealthcare.core.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ssu.soongsilhealthcare.core.data.local.dao.DietDao
import com.ssu.soongsilhealthcare.core.data.local.dao.ExerciseDao
import com.ssu.soongsilhealthcare.core.data.local.entity.DietEntity
import com.ssu.soongsilhealthcare.core.data.local.entity.ExerciseEntity

@Database(
    entities = [ExerciseEntity::class, DietEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
    abstract fun dietDao(): DietDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "soongsil_healthcare.db"
                ).build().also { instance = it }
            }
        }
    }
}

package com.ssu.soongsilhealthcare.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ssu.soongsilhealthcare.core.data.local.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Insert
    suspend fun insertExercise(exercise: ExerciseEntity)

    @Delete
    suspend fun deleteExercise(exercise: ExerciseEntity)

    @Query("SELECT * FROM exercises WHERE userId = :userId AND date = :date ORDER BY id DESC")
    fun getExercisesByDate(userId: String, date: String): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM exercises WHERE userId = :userId ORDER BY date DESC, id DESC")
    fun getAllExercises(userId: String): Flow<List<ExerciseEntity>>
}

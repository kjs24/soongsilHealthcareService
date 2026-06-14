package com.ssu.soongsilhealthcare.core.data.repository

import com.ssu.soongsilhealthcare.core.data.local.dao.ExerciseDao
import com.ssu.soongsilhealthcare.core.data.local.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow

class ExerciseRepository(
    private val exerciseDao: ExerciseDao
) {
    fun getExercisesByDate(userId: String, date: String): Flow<List<ExerciseEntity>> {
        return exerciseDao.getExercisesByDate(userId, date)
    }

    fun getAllExercises(userId: String): Flow<List<ExerciseEntity>> {
        return exerciseDao.getAllExercises(userId)
    }

    suspend fun addExercise(exercise: ExerciseEntity) {
        exerciseDao.insertExercise(exercise)
    }

    suspend fun deleteExercise(exercise: ExerciseEntity) {
        exerciseDao.deleteExercise(exercise)
    }
}

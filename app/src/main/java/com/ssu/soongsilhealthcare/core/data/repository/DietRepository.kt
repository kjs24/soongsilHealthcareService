package com.ssu.soongsilhealthcare.core.data.repository

import com.ssu.soongsilhealthcare.core.data.local.dao.DietDao
import com.ssu.soongsilhealthcare.core.data.local.entity.DietEntity
import kotlinx.coroutines.flow.Flow

class DietRepository(
    private val dietDao: DietDao
) {
    fun getDietsByDate(userId: String, date: String): Flow<List<DietEntity>> {
        return dietDao.getDietsByDate(userId, date)
    }

    fun getAllDiets(userId: String): Flow<List<DietEntity>> {
        return dietDao.getAllDiets(userId)
    }

    fun getFavoriteDiets(userId: String): Flow<List<DietEntity>> {
        return dietDao.getFavoriteDiets(userId)
    }

    suspend fun addDiet(diet: DietEntity) {
        dietDao.insertDiet(diet)
    }

    suspend fun deleteDiet(diet: DietEntity) {
        dietDao.deleteDiet(diet)
    }

    suspend fun updateFavorite(id: Int, isFavorite: Boolean) {
        dietDao.updateFavorite(id, isFavorite)
    }
}

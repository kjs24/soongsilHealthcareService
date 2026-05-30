package com.ssu.soongsilhealthcare.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ssu.soongsilhealthcare.core.data.local.entity.DietEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DietDao {
    @Insert
    suspend fun insertDiet(diet: DietEntity)

    @Delete
    suspend fun deleteDiet(diet: DietEntity)

    @Query("SELECT * FROM diets WHERE userId = :userId AND date = :date ORDER BY id DESC")
    fun getDietsByDate(userId: String, date: String): Flow<List<DietEntity>>

    @Query("SELECT * FROM diets WHERE userId = :userId ORDER BY date DESC, id DESC")
    fun getAllDiets(userId: String): Flow<List<DietEntity>>

    @Query("SELECT * FROM diets WHERE userId = :userId AND isFavorite = 1 ORDER BY foodName ASC")
    fun getFavoriteDiets(userId: String): Flow<List<DietEntity>>

    @Query("UPDATE diets SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Int, isFavorite: Boolean)
}

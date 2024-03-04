package com.maxxxwk.mornhousetesttask.screens.main.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert
    suspend fun insertHistoryEntity(entity: HistoryEntity)

    @Query("SELECT history.id, history.number, facts.fact_text FROM history INNER JOIN facts ON history.number = facts.number ORDER BY history.id DESC")
    fun getHistoryFlow(): Flow<List<HistoryItem>>
}

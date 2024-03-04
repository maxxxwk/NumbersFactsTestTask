package com.maxxxwk.mornhousetesttask.screens.main.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface FactsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFact(factEntity: FactEntity)
}

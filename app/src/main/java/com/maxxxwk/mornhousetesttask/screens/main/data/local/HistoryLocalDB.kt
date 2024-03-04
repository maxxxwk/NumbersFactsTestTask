package com.maxxxwk.mornhousetesttask.screens.main.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FactEntity::class, HistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class HistoryLocalDB : RoomDatabase() {
    abstract val factsDao: FactsDao
    abstract val historyDao: HistoryDao
}

package com.maxxxwk.mornhousetesttask.di

import android.content.Context
import androidx.room.Room
import com.maxxxwk.mornhousetesttask.screens.main.data.local.FactsDao
import com.maxxxwk.mornhousetesttask.screens.main.data.local.HistoryDao
import com.maxxxwk.mornhousetesttask.screens.main.data.local.HistoryLocalDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDBModule {
    @Provides
    fun provideHistoryDao(historyLocalDB: HistoryLocalDB): HistoryDao = historyLocalDB.historyDao

    @Provides
    fun provideFactsDao(historyLocalDB: HistoryLocalDB): FactsDao = historyLocalDB.factsDao

    @Provides
    @Singleton
    fun provideHistoryLocalDB(context: Context): HistoryLocalDB {
        return Room.databaseBuilder(
            context,
            HistoryLocalDB::class.java,
            "facts_history"
        ).build()
    }
}

package com.maxxxwk.mornhousetesttask.screens.main.data

import com.maxxxwk.mornhousetesttask.core.coroutines.DispatchersProvider
import com.maxxxwk.mornhousetesttask.screens.main.data.local.FactEntity
import com.maxxxwk.mornhousetesttask.screens.main.data.local.FactsDao
import com.maxxxwk.mornhousetesttask.screens.main.data.local.HistoryDao
import com.maxxxwk.mornhousetesttask.screens.main.data.local.HistoryEntity
import com.maxxxwk.mornhousetesttask.screens.main.data.local.HistoryItem
import com.maxxxwk.mornhousetesttask.screens.main.data.remote.NumbersApiService
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class FactsHistoryRepository @Inject constructor(
    private val numbersApiService: NumbersApiService,
    private val factsDao: FactsDao,
    private val historyDao: HistoryDao,
    private val dispatchersProvider: DispatchersProvider
) {

    suspend fun loadFact(number: Int): Result<Unit> = withContext(dispatchersProvider.io) {
        try {
            val factText = numbersApiService.getFactAboutNumber(number)
            saveFactToHistory(factText)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loadRandomFact(): Result<Unit> = withContext(dispatchersProvider.io) {
        try {
            val factText = numbersApiService.getFactAboutRandomNumber()
            saveFactToHistory(factText)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun saveFactToHistory(
        fact: String
    ): Result<Unit> = withContext(dispatchersProvider.io) {
        runCatching {
            val number = fact.split(" ").first().toInt()
            factsDao.insertFact(
                FactEntity(
                    number = number,
                    factText = fact
                )
            )
            historyDao.insertHistoryEntity(HistoryEntity(number = number))
        }
    }

    fun getHistoryFlow(): Flow<List<HistoryItem>> = historyDao.getHistoryFlow()
        .flowOn(dispatchersProvider.io)
}

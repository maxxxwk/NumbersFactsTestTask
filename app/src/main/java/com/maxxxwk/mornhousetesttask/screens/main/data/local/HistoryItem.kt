package com.maxxxwk.mornhousetesttask.screens.main.data.local

import androidx.room.ColumnInfo

data class HistoryItem(
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("number")
    val number: Int,
    @ColumnInfo("fact_text")
    val factText: String
)

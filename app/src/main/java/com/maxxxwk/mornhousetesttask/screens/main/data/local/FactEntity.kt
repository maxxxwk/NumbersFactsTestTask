package com.maxxxwk.mornhousetesttask.screens.main.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "facts")
data class FactEntity(
    @PrimaryKey
    @ColumnInfo("number")
    val number: Int,
    @ColumnInfo("fact_text")
    val factText: String
)

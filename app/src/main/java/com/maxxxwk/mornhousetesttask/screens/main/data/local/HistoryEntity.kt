package com.maxxxwk.mornhousetesttask.screens.main.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "history",
    foreignKeys = [
        ForeignKey(
            entity = FactEntity::class,
            parentColumns = ["number"],
            childColumns = ["number"]
        )
    ]
)
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int = 0,
    @ColumnInfo(
        name = "number",
        index = true
    )
    val number: Int
)

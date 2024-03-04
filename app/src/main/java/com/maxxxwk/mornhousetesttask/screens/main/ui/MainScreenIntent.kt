package com.maxxxwk.mornhousetesttask.screens.main.ui

import androidx.annotation.StringRes
import com.maxxxwk.mornhousetesttask.screens.main.data.local.HistoryItem

sealed interface MainScreenIntent {
    data class NumberInputFieldValueChanged(val value: String) : MainScreenIntent
    data object GetFactButtonClicked : MainScreenIntent
    data object GetFactAboutRandomNumberButtonClicked : MainScreenIntent
    data class UpdateHistory(val history: List<HistoryItem>) : MainScreenIntent
    data class WarningEvent(@StringRes val warningTextResId: Int) : MainScreenIntent
    data object WarningEventConsumed : MainScreenIntent
}

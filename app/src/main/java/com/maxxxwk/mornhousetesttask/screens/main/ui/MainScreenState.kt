package com.maxxxwk.mornhousetesttask.screens.main.ui

import androidx.compose.runtime.Immutable
import com.maxxxwk.mornhousetesttask.screens.main.data.local.HistoryItem
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class MainScreenState(
    val numberInputFieldValue: String = "",
    val historyState: HistoryState = HistoryState.Loading,
    val isLoading: Boolean = false,
    val warningEvent: StateEventWithContent<Int> = consumed()
)

@Immutable
sealed interface HistoryState {
    data object Loading : HistoryState
    data class HistoryItems(val items: List<HistoryItem>) : HistoryState
}

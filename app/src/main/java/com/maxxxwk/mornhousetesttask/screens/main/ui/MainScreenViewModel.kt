package com.maxxxwk.mornhousetesttask.screens.main.ui

import androidx.lifecycle.viewModelScope
import com.maxxxwk.mornhousetesttask.R
import com.maxxxwk.mornhousetesttask.core.viewmodel.MVIViewModel
import com.maxxxwk.mornhousetesttask.screens.main.data.FactsHistoryRepository
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainScreenViewModel @Inject constructor(
    private val factsHistoryRepository: FactsHistoryRepository
) : MVIViewModel<MainScreenIntent, MainScreenState>(MainScreenState()) {

    init {
        factsHistoryRepository.getHistoryFlow()
            .onEach { intents.send(MainScreenIntent.UpdateHistory(it)) }
            .launchIn(viewModelScope)
    }

    fun getFact() {
        viewModelScope.launch {
            intents.send(MainScreenIntent.GetFactButtonClicked)
        }
    }

    fun getRandomFact() {
        viewModelScope.launch {
            intents.send(MainScreenIntent.GetFactAboutRandomNumberButtonClicked)
        }
    }

    fun onNumberTextFieldValueChanged(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            viewModelScope.launch {
                intents.send(MainScreenIntent.NumberInputFieldValueChanged(value))
            }
        }
    }

    fun warningEventConsumed() {
        viewModelScope.launch {
            intents.send(MainScreenIntent.WarningEventConsumed)
        }
    }

    override fun reduce(
        intent: MainScreenIntent
    ): MainScreenState = when (intent) {
        MainScreenIntent.GetFactAboutRandomNumberButtonClicked -> {
            viewModelScope.launch {
                factsHistoryRepository.loadRandomFact().onFailure {
                    intents.send(
                        MainScreenIntent.WarningEvent(
                            R.string.fact_loading_failed_error_message
                        )
                    )
                }
            }
            state.value.copy(isLoading = true)
        }

        MainScreenIntent.GetFactButtonClicked -> {
            viewModelScope.launch {
                val numberInputFieldValue = state.value.numberInputFieldValue

                if (numberInputFieldValue.isEmpty()) {
                    intents.send(
                        MainScreenIntent.WarningEvent(
                            R.string.enter_number_warning
                        )
                    )
                } else {
                    factsHistoryRepository.loadFact(
                        state.value.numberInputFieldValue.toInt()
                    ).onFailure {
                        intents.send(
                            MainScreenIntent.WarningEvent(
                                R.string.fact_loading_failed_error_message
                            )
                        )
                    }
                }
            }
            state.value.copy(isLoading = true)
        }

        is MainScreenIntent.NumberInputFieldValueChanged -> state.value.copy(
            numberInputFieldValue = intent.value
        )

        is MainScreenIntent.UpdateHistory -> state.value.copy(
            historyState = HistoryState.HistoryItems(intent.history),
            isLoading = false
        )

        is MainScreenIntent.WarningEvent -> state.value.copy(
            warningEvent = triggered(intent.warningTextResId),
            isLoading = false
        )

        MainScreenIntent.WarningEventConsumed -> state.value.copy(
            warningEvent = consumed()
        )
    }
}

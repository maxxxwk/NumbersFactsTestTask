package com.maxxxwk.mornhousetesttask.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

abstract class MVIViewModel<INTENT, STATE>(initialState: STATE) : ViewModel() {
    protected val intents = Channel<INTENT>(Channel.UNLIMITED)
    val state: StateFlow<STATE> = intents.consumeAsFlow()
        .map(::reduce)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = initialState
        )

    abstract fun reduce(intent: INTENT): STATE
}

package com.maxxxwk.mornhousetesttask.screens.main.ui

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maxxxwk.mornhousetesttask.R
import com.maxxxwk.mornhousetesttask.screens.main.data.local.HistoryItem
import de.palm.composestateevents.EventEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel,
    openDetails: (HistoryItem) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    EventEffect(
        event = state.warningEvent,
        onConsumed = viewModel::warningEventConsumed,
        action = { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
    )
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.main_screen_title)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(it)
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            NumberForm(
                modifier = Modifier.fillMaxWidth(),
                numberInputFieldValue = state.numberInputFieldValue,
                isEnabled = !state.isLoading,
                onNumberTextFieldValueChanged = viewModel::onNumberTextFieldValueChanged,
                getFact = viewModel::getFact,
                getRandomFact = viewModel::getRandomFact
            )
            HistoryList(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                historyState = state.historyState,
                onItemClicked = openDetails
            )
        }
    }
}

@Composable
private fun HistoryList(
    modifier: Modifier = Modifier,
    historyState: HistoryState,
    onItemClicked: (HistoryItem) -> Unit
) {
    when (historyState) {
        is HistoryState.HistoryItems -> HistoryListContent(
            modifier = modifier,
            items = historyState.items,
            onItemClicked = onItemClicked
        )

        HistoryState.Loading -> HistoryListLoading(
            modifier = modifier
        )
    }
}

@Composable
private fun HistoryListContent(
    modifier: Modifier = Modifier,
    items: List<HistoryItem>,
    onItemClicked: (HistoryItem) -> Unit
) {
    val lazyListState = rememberLazyListState()
    LaunchedEffect(items) { lazyListState.animateScrollToItem(0) }
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = lazyListState,
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(
            items = items,
            key = { it.id }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.secondary
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                )
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { onItemClicked(it) }
                        .padding(8.dp),
                    text = it.factText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun HistoryListLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun NumberForm(
    modifier: Modifier = Modifier,
    numberInputFieldValue: String,
    isEnabled: Boolean,
    onNumberTextFieldValueChanged: (String) -> Unit,
    getFact: () -> Unit,
    getRandomFact: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = numberInputFieldValue,
            onValueChange = onNumberTextFieldValueChanged,
            label = { Text(text = stringResource(R.string.number_text_field_label)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            enabled = isEnabled
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                onClick = getFact,
                enabled = isEnabled
            ) {
                Text(
                    text = stringResource(R.string.get_fact_button_text),
                    textAlign = TextAlign.Center
                )
            }
            Button(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                onClick = getRandomFact,
                enabled = isEnabled
            ) {
                Text(
                    text = stringResource(R.string.get_random_fact_button_text),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

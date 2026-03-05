package com.lucasdev.jornadacerta.screens.history.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.lucasdev.jornadacerta.common.model.RegisterUiData
import com.lucasdev.jornadacerta.screens.history.HistoryViewModel
import com.lucasdev.jornadacerta.screens.history.data.model.HistoryUiState
import com.lucasdev.jornadacerta.ui.theme.JornadaCertaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    navController: NavController,
    viewModel: HistoryViewModel = hiltViewModel()
) {

    val historyUiState by viewModel.uiHistory.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadAllRegisters()
    }

    Scaffold(
        topBar = {
            ToolBarWithBackButton(
                title = "Histórico",
                onBackClick = {
                    navController.popBackStack()
                }
            )
        },
        content = { paddingValues ->
            HistoryContent(
                modifier = Modifier
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        start = paddingValues.calculateLeftPadding(LayoutDirection.Ltr),
                        end = paddingValues.calculateRightPadding(LayoutDirection.Ltr),
                        bottom = 0.dp
                    ),
                historyUiState = historyUiState
            )
        }
    )

}

@Composable
fun HistoryContent(
    modifier: Modifier,
    historyUiState: HistoryUiState
) {

    if(historyUiState.registers.isEmpty()){
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = historyUiState.emptyMessage,
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }else{
        HistoryDayList(
            modifier = modifier,
            registerList = historyUiState.registers
        )
    }

}

@Composable
fun HistoryDayList(
    modifier: Modifier,
    registerList: List<RegisterUiData>
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        items(registerList){
            HistoryDayItem(
                modifier = Modifier,
                registerUiData = it
            )
        }
    }
}

@Composable
fun HistoryDayItem(
    modifier: Modifier,
    registerUiData: RegisterUiData) {
    Column(
        modifier = modifier
            .padding(start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SuggestionChip(
            onClick = {},
            label = {
                Text(
                    text = registerUiData.dateFormated ?: "",
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            border = SuggestionChipDefaults.suggestionChipBorder(
                enabled = true,
                borderColor = Color.Transparent
            ),
            shape = RoundedCornerShape(16.dp),
            colors = SuggestionChipDefaults.suggestionChipColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                labelColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = modifier
                .fillMaxWidth()
                .height(110.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.CenterVertically),
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(space = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Entrada",
                            fontWeight = FontWeight.Normal,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = registerUiData.startTime ?: "--:--",
                            fontWeight = FontWeight.Normal,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    HorizontalDivider(
                        thickness = 0.5.dp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f))
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(space = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Saída",
                            fontWeight = FontWeight.Normal,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = registerUiData.endTime ?: "--:--",
                            fontWeight = FontWeight.Normal,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(space = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Saldo",
                            fontWeight = FontWeight.Normal,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = registerUiData.balanceFormatted,
                            fontWeight = FontWeight.Normal,
                            style = MaterialTheme.typography.bodyLarge,
                            color = registerUiData.getBalanceColor()
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(
    name = "Light mode preview",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark mode preview",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun HistoryScreenPreview() {


    JornadaCertaTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                ToolBarWithBackButton(
                    title = "Histórico",
                    onBackClick = {}
                )
            },
            content = { paddingValues ->

                HistoryContent(
                    modifier = Modifier
                        .padding(paddingValues),
                    historyUiState = HistoryUiState(
                        registers = listOf(
                            RegisterUiData(
                                id = 0,
                                date = "2026-03-05",
                                startTime = "08:00",
                                endTime ="16:45",
                                workload = "08:48",
                                estimatedExitTime = "16:48",
                                balance = "00:03",
                                isBalanceNegative = true,
                            ),
                            RegisterUiData(
                                id = 0,
                                date = "2026-03-04",
                                startTime = "08:00",
                                endTime ="18:30",
                                workload = "08:48",
                                estimatedExitTime = "16:48",
                                balance = "00:30",
                                isBalanceNegative = false
                            ),
                            RegisterUiData(
                                id = 0,
                                date = "2026-03-04",
                                startTime = "08:00",
                                endTime ="18:30",
                                workload = "08:48",
                                estimatedExitTime = "16:48",
                                balance = "00:30",
                                isBalanceNegative = false
                            )
                        )
                    )
                )
            })

    }
}
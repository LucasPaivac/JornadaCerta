package com.lucasdev.jornadacerta.screens.register.presentation.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SplitButtonDefaults
import androidx.compose.material3.SplitButtonLayout
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lucasdev.jornadacerta.R
import com.lucasdev.jornadacerta.common.model.RegisterUiData
import com.lucasdev.jornadacerta.screens.register.presentation.RegisterViewModel
import com.lucasdev.jornadacerta.screens.register.presentation.data.model.RegisterUiState
import com.lucasdev.jornadacerta.ui.theme.JornadaCertaTheme
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import com.maxkeppeler.sheets.duration.DurationDialog
import com.maxkeppeler.sheets.duration.models.DurationConfig
import com.maxkeppeler.sheets.duration.models.DurationFormat
import com.maxkeppeler.sheets.duration.models.DurationSelection
import java.time.Duration
import java.time.LocalTime
import java.util.Locale


@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel = hiltViewModel()
) {

    val registerUiState by viewModel.uiRegister.collectAsState()
    val recentRegisters by viewModel.recentHistory.collectAsState()
    val currentTime by viewModel.currentTime.collectAsState()
    val currentWorkload by viewModel.uiSelectedWorkload.collectAsState()

    val state = rememberUseCaseState()

    EntryTimePicker(
        state = state,
        onEntryChanged = { customTime ->
            viewModel.onRegisterEntryOut(customTime)
        }
    )

    Scaffold(
        floatingActionButton = {
            RegisterEntryOutButton(
                label = registerUiState.labelButton,
                buttonColor = registerUiState.getButtonColor().first,
                onButtonColor = registerUiState.getButtonColor().second,
                isEnabled = registerUiState.isButtonEnabled,
                onClick = { state.show() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { paddingValues ->
            RegisterContent(
                modifier = Modifier
                    .padding(paddingValues),
                registerUiState = registerUiState,
                recentRegisters = recentRegisters,
                currentTime = currentTime,
                currentWorkload = currentWorkload,
                onWorkloadChanged = { newWorkload ->
                    viewModel.updateWorkload(newWorkload)
                },
                onHistoryClick = {
                    navController.navigate(route = "history")
                },
                onEntryChanged = { newEntry ->
                    viewModel.updateEntry(newEntry)
                })
        }
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EntryTimePicker(
    state: UseCaseState,
    onEntryChanged: (String) -> Unit
) {
    ClockDialog(
        state = state,
        config = ClockConfig(
            defaultTime = LocalTime.now(),
            is24HourFormat = true
        ),
        selection = ClockSelection.HoursMinutes(
            onPositiveClick = { hours, minutes ->
                val formattedTime = String.format("%02d:%02d", hours, minutes)
                onEntryChanged(formattedTime)
            }
        )
    )
}

@Composable
private fun RegisterEntryOutButton(
    label: String,
    buttonColor: Color,
    onButtonColor: Color,
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier,
) {
    ElevatedButton(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = buttonColor,
            contentColor = onButtonColor,
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(8.dp),
        enabled = isEnabled,
        content = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(42.dp),
                    painter = painterResource(R.drawable.ic_time_logo),
                    contentDescription = "Clock Icon"
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    )
}

@Composable
fun RegisterContent(
    modifier: Modifier,
    registerUiState: RegisterUiState,
    recentRegisters: List<RegisterUiData>,
    currentTime: LocalTime,
    currentWorkload: String,
    onWorkloadChanged: (String) -> Unit,
    onHistoryClick: () -> Unit,
    onEntryChanged: (String) -> Unit
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp, horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(32.dp)
                        .padding(end = 4.dp),
                    painter = painterResource(R.drawable.ic_time_logo),
                    contentDescription = "Timer logo",
                )

                Text(
                    text = "Jornada Certa",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
        }

        item {
            RegisterMainCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillParentMaxHeight(0.35f),
                registerUiState = registerUiState,
                currentTime = currentTime,
                currentWorkload = currentWorkload,
                onWorkloadChanged = onWorkloadChanged,
                onEntryChanged = onEntryChanged
            )
        }

        item {
            Spacer(Modifier.size(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillParentMaxHeight(0.4f),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    RecentRegisterSession(
                        modifier = Modifier,
                        recentRegisters = recentRegisters
                    )

                    Button(
                        onClick = onHistoryClick,
                        modifier = Modifier,
                        enabled = true,
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        ),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_history),
                            contentDescription = "History Icon Button"
                        )
                        Spacer(Modifier.size(4.dp))

                        Text(
                            text = "Histórico",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }

    }

}


@Composable
fun RegisterMainCard(
    modifier: Modifier,
    registerUiState: RegisterUiState,
    currentTime: LocalTime,
    currentWorkload: String,
    onWorkloadChanged: (String) -> Unit,
    onEntryChanged: (String) -> Unit
) {

    val journeyProgress = remember(currentTime, registerUiState.register) {

        val start = registerUiState.register?.startTime?.let { LocalTime.parse(it) }
        val workload = registerUiState.register?.workload?.let { LocalTime.parse(it) }
        val estimatedExit = registerUiState.register?.estimatedExitTime?.let { LocalTime.parse(it) }
        val endTime = registerUiState.register?.endTime?.let { LocalTime.parse(it) }

        if (start != null && estimatedExit != null && workload != null && endTime == null) {

            val diff = Duration.between(currentTime, estimatedExit)
            val remainingText = if (diff.isNegative) "00:00:00" else formatDuration(diff)
            val totalMinutes = (workload.hour * 60) + workload.minute
            val minutesPassed = Duration.between(start, currentTime).toMinutes()
            val progressPercent =
                (minutesPassed.toFloat() / totalMinutes.toFloat()).coerceIn(0f, 1f)

            remainingText to progressPercent

        } else {
            "--:--:--" to 0f
        }
    }

    val (remainingText, progressValue) = journeyProgress

    var progressTarget by remember { mutableStateOf(0f) }

    progressTarget = progressValue

    val animatedProgress by animateFloatAsState(
        targetValue = progressTarget,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    ElevatedCard(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        4.dp,
                        alignment = Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = registerUiState.welcomeMessage,
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.titleLarge,
                    )

                    if (registerUiState.register?.isWorkInProgress ?: false) {
                        val state = rememberUseCaseState()

                        EntryTimePicker(
                            state = state,
                            onEntryChanged = { customTime ->
                                onEntryChanged(customTime)
                            }
                        )

                        @OptIn(ExperimentalMaterial3ExpressiveApi::class)
                        IconButton(
                            modifier = Modifier
                                .size(IconButtonDefaults.extraSmallIconSize),
                            onClick = { state.show() },
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_edit),
                                contentDescription = "Edit Icon",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 36.dp, end = 36.dp)
                        .height(12.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )

                Spacer(Modifier.size(4.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = registerUiState.outMessage,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineLarge
                )
            }

            var expanded by remember { mutableStateOf(false) }
            val workLoadInSeconds = LocalTime.parse(currentWorkload).toSecondOfDay().toLong()
            val durationState = rememberUseCaseState()

            WorkloadTimePicker(
                customCurrentTime = workLoadInSeconds,
                state = durationState,
                onWorkloadChanged = onWorkloadChanged
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .size(36.dp),
                        painter = painterResource(R.drawable.ic_go_out),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = "Go out icon"
                    )
                    Text(
                        modifier = Modifier,
                        text = remainingText,
                        fontFamily = FontFamily.Monospace
                    )
                }

                Box() {
                    WorkloadSplitButton(
                        currentWorkload = currentWorkload,
                        onMainClick = {},
                        onTrailingClick = { expanded = true }
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("06:00") },
                            onClick = {
                                onWorkloadChanged("06:00")
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("08:48") },
                            onClick = {
                                onWorkloadChanged("08:48")
                                expanded = false
                            }
                        )
                        HorizontalDivider()
                        DropdownMenuItem(
                            text = { Text("Customizar...") },
                            onClick = {
                                durationState.show()
                                expanded = false
                            }
                        )
                    }
                }

            }

        }
    }

}

@Composable
fun RecentRegisterSession(
    modifier: Modifier,
    recentRegisters: List<RegisterUiData>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 18.dp, end = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(32.dp),
                painter = painterResource(R.drawable.ic_history),
                contentDescription = "History Icon label"
            )
            Spacer(Modifier.size(4.dp))
            Text(
                text = "Últimos 03 dias",
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(Modifier.size(16.dp))

        Row(
            modifier = modifier
                .fillMaxWidth()
        ) {
            val colunas = listOf("Data", "Entrada", "Saída", "Saldo")

            colunas.forEach { titulo ->
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center // Alinha o conteúdo no centro do Box
                ) {
                    Text(
                        text = titulo,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            ),
            modifier = modifier
                .fillMaxWidth()
                .height(100.dp),
        ) {
            if (recentRegisters.isEmpty()) {
                Box(
                    modifier = modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sem registros",
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                RecentRegistersList(
                    modifier = modifier,
                    recentRegisters = recentRegisters
                )
            }
        }

    }
}

@Composable
fun RecentRegistersList(
    modifier: Modifier,
    recentRegisters: List<RegisterUiData>
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterVertically)
    ) {
        items(recentRegisters) {
            RecentRegisterItem(
                modifier = modifier,
                registerUiData = it
            )
        }
    }

}

@Composable
fun RecentRegisterItem(
    modifier: Modifier,
    registerUiData: RegisterUiData
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
        ) {
            val colunas = listOf(
                registerUiData.dateFormated?.substring(0, 5) ?: "--/--",
                registerUiData.startTime ?: "--:--",
                registerUiData.endTime ?: "--:--",
            )

            colunas.forEach { titulo ->
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center // Alinha o conteúdo no centro do Box
                ) {
                    Text(
                        text = titulo,
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center // Alinha o conteúdo no centro do Box
            ) {
                Text(
                    text = registerUiData.balanceFormatted,
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.titleSmall,
                    color = registerUiData.getBalanceColor()
                )
            }

        }
        HorizontalDivider(
            thickness = 0.5.dp,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WorkloadTimePicker(
    customCurrentTime: Long,
    state: UseCaseState,
    onWorkloadChanged: (String) -> Unit
) {
    DurationDialog(
        state = state,
        config = DurationConfig(
            timeFormat = DurationFormat.HH_MM,
            currentTime = customCurrentTime,
        ),
        selection = DurationSelection(
            onPositiveClick = { finalTimeInSeconds ->
                val hours = finalTimeInSeconds / 3600
                val minutes = (finalTimeInSeconds % 3600) / 60
                val formattedTime = String.format(Locale.getDefault(), "%02d:%02d", hours, minutes)

                onWorkloadChanged(formattedTime)
            }
        )
    )
}


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun WorkloadSplitButton(
    currentWorkload: String,
    onMainClick: () -> Unit,
    onTrailingClick: () -> Unit
) {

    SplitButtonLayout(
        leadingButton = {
            SplitButtonDefaults.LeadingButton(
                onClick = onMainClick,
                enabled = true,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                contentPadding = PaddingValues(
                    horizontal = 12.dp,
                    vertical = 0.dp
                ),
            ) {
                Icon(
                    modifier = Modifier
                        .size(14.dp),
                    painter = painterResource(R.drawable.ic_workload),
                    contentDescription = "Workload icon"
                )
                Spacer(Modifier.size(4.dp))
                Text(
                    text = currentWorkload,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Monospace,


                    )
            }
        },
        trailingButton = {
            SplitButtonDefaults.TrailingButton(
                onClick = onTrailingClick,
                enabled = true,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                contentPadding = PaddingValues(
                    horizontal = 0.dp,
                    vertical = 0.dp
                ),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_down),
                    contentDescription = "Abrir opções de carga"
                )
            }
        }
    )
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
fun PreviewRegisterContent() {

    val registerUiState = RegisterUiState(
        register =
            RegisterUiData(
                id = 0,
                date = "2026-03-05",
                startTime = "08:00",
                endTime ="16:45",
                workload = "08:48",
                estimatedExitTime = "16:48",
                balance = "00:03",
                isBalanceNegative = true
            )
    )

    JornadaCertaTheme {
        Scaffold(
            floatingActionButton = {
                RegisterEntryOutButton(
                    label = registerUiState.labelButton,
                    buttonColor = registerUiState.getButtonColor().first,
                    onButtonColor = registerUiState.getButtonColor().second,
                    isEnabled = registerUiState.isButtonEnabled,
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                )
            },
            floatingActionButtonPosition = FabPosition.Center,
            content = { paddingValues ->
                RegisterContent(
                    modifier = Modifier
                        .padding(paddingValues),
                    registerUiState = registerUiState,
                    recentRegisters = listOf(
                        registerUiState.register ?: RegisterUiData(
                            id = 0,
                            date = "2026-03-05",
                            startTime = "08:00",
                            workload = "08:48",
                            estimatedExitTime = "16:48",
                        ),
                        RegisterUiData(
                            id = 0,
                            date = "2026-03-04",
                            startTime = "08:00",
                            endTime = "18:30",
                            workload = "10:00",
                            balance = "00:30",
                            isBalanceNegative = false
                        ),
                        RegisterUiData(
                            id = 0,
                            date = "2026-03-03",
                            startTime = "08:00",
                            endTime = "18:30",
                            workload = "10:00",
                            balance = "00:30",
                            isBalanceNegative = false
                        ),
                    ),
                    currentTime = LocalTime.now(),
                    currentWorkload = "08:48",
                    onWorkloadChanged = {},
                    onHistoryClick = {},
                    onEntryChanged = {})
            }
        )

    }
}

private fun formatDuration(duration: Duration): String {
    val hours = duration.toHours()
    val minutes = duration.toMinutesPart()
    val seconds = duration.toSecondsPart()
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

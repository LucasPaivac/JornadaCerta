package com.lucasdev.jornadacerta.screens.register.presentation.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.drawable.Icon
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SplitButtonDefaults
import androidx.compose.material3.SplitButtonLayout
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lucasdev.jornadacerta.R
import com.lucasdev.jornadacerta.screens.register.presentation.RegisterViewModel
import com.lucasdev.jornadacerta.screens.register.presentation.data.model.RegisterUiState
import com.lucasdev.jornadacerta.ui.theme.JornadaCertaTheme
import java.time.Duration
import java.time.LocalTime


@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel = hiltViewModel()
) {

    val registerUiState by viewModel.uiRegister.collectAsState()
    val currentTime by viewModel.currentTime.collectAsState()

    RegisterContent(registerUiState, currentTime)

}

@Composable
fun RegisterContent(registerUiState: RegisterUiState, currentTime: LocalTime) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 24.dp),
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
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }

        item {
            RegisterMainCard(
                Modifier
                    .fillMaxWidth()
                    .fillParentMaxHeight(0.38f),
                registerUiState,
                currentTime
            )
        }

        item {
            var expanded by remember { mutableStateOf(false) }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ){

                ElevatedButton(
                    onClick = { },
                    modifier = Modifier,
                    enabled = true,
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 8.dp, pressedElevation = 4.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                ) {
                    Text(
                        text = "Histórico",
                        style = MaterialTheme.typography.bodySmall)
                }

                Box(){
                    WorkloadSplitButton(
                        currentWorkload = "08:48",
                        onMainClick = {},
                        onTrailingClick = {expanded = true}
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("06:00") },
                            onClick = { /* Atualizar VM e fechar */ expanded = false }
                        )
                        DropdownMenuItem(
                            text = { Text("08:48") },
                            onClick = { /* Atualizar VM e fechar */ expanded = false }
                        )
                        HorizontalDivider()
                        DropdownMenuItem(
                            text = { Text("Customizar...") },
                            onClick = { /* Navegar para config */ expanded = false }
                        )
                    }
                }

                ElevatedButton(
                    onClick = { },
                    modifier = Modifier,
                    enabled = true,
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 8.dp, pressedElevation = 4.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                ) {
                    Text(
                        text = "Customizar",
                        style = MaterialTheme.typography.bodySmall)
                }

            }
        }

        item {
            
        }

    }
}

@Composable
fun RegisterMainCard(
    modifier: Modifier,
    registerUiState: RegisterUiState,
    currentTime: LocalTime
) {

    val journeyProgress = remember(currentTime, registerUiState.register) {

        val start = registerUiState.register?.startTime?.let { LocalTime.parse(it) }
        val workload = registerUiState.register?.workload?.let { LocalTime.parse(it) }
        val estimatedExit = registerUiState.register?.estimatedExitTime?.let { LocalTime.parse(it) }

        if (start != null && estimatedExit != null && workload != null) {

            val diff = Duration.between(currentTime, estimatedExit)
            val remainingText = if (diff.isNegative) "00:00:00" else formatDuration(diff)
            val totalMinutes = (workload.hour * 60) + workload.minute
            val minutesPassed = Duration.between(start, currentTime).toMinutes()
            val progressPercent = (minutesPassed.toFloat() / totalMinutes.toFloat()).coerceIn(0f, 1f)

            remainingText to progressPercent

        } else {
            "--:--:--" to 0f
        }
    }

    val (remainingText, progressValue) = journeyProgress

    var progressTarget by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        progressTarget = progressValue
    }

    val animatedProgress by animateFloatAsState(
        targetValue = progressTarget,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    ElevatedCard(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(
                    text = registerUiState.welcomeMessage,
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.titleLarge
                )

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
                    text = "Sair às ${registerUiState.register?.estimatedExitTime ?: "--:--"}",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.displaySmall
                )

                Spacer(Modifier.size(8.dp))

                SuggestionChip(
                    modifier = Modifier,
                    onClick = {},
                    label = {
                        Row() {
                            Icon(
                                painter = painterResource(R.drawable.ic_go_out),
                                contentDescription = "Go out icon"
                            )
                            Text(
                                text = remainingText,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                    },
                    border = SuggestionChipDefaults.suggestionChipBorder(
                        enabled = true,
                        borderColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(16.dp),
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                )

            }

        }
    }

}

@Composable
fun RegisterPremiumInfo(modifier: Modifier = Modifier) {

}


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun WorkloadSplitButton(
    currentWorkload: String,
    onMainClick: () -> Unit,
    onTrailingClick: () -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    SplitButtonLayout(
        leadingButton = {
            SplitButtonDefaults.LeadingButton(
                onClick = onMainClick,
                enabled = true,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 4.dp),
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 8.dp),
            ) {
                Icon(
                    modifier = Modifier
                        .size(18.dp),
                    painter = painterResource(R.drawable.ic_workload),
                    contentDescription = "Workload icon"
                )
                Spacer(Modifier.size(4.dp))
                Text(text = currentWorkload)
            }
        },
        trailingButton = {
            SplitButtonDefaults.TrailingButton(
                onClick = onTrailingClick,
                enabled = true,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 4.dp),
                contentPadding = PaddingValues(
                    horizontal = 0.dp,
                    vertical = 8.dp),
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
    JornadaCertaTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) {

            val currentTime = LocalTime.parse("06:00:00")

            RegisterContent(RegisterUiState(), currentTime)
        }

    }
}

private fun formatDuration(duration: Duration): String {
    val hours = duration.toHours()
    val minutes = duration.toMinutesPart()
    val seconds = duration.toSecondsPart()
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

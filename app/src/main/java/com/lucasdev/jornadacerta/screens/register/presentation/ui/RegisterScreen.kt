package com.lucasdev.jornadacerta.screens.register.presentation.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lucasdev.jornadacerta.R
import com.lucasdev.jornadacerta.screens.register.presentation.RegisterViewModel
import com.lucasdev.jornadacerta.screens.register.presentation.data.model.RegisterUiState
import com.lucasdev.jornadacerta.ui.theme.JornadaCertaTheme

@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel = hiltViewModel()
) {

    val register by viewModel.uiRegister.collectAsState()

    RegisterContent(register)
    
}

@Composable
fun RegisterContent(register: RegisterUiState) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
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
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillParentMaxHeight(0.4f)
                    .padding(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 16.dp
                )
            ){
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center

                ){
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        Text(
                            text = register.welcomeMessage,
                            fontWeight = FontWeight.Normal,
                            style = MaterialTheme.typography.titleLarge
                        )

                        LinearProgressIndicator(
                            progress = { register.statusBar },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 36.dp, end = 36.dp)
                                .height(12.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        )

                        Text(
                            text = "Sair às ${register.register?.endTime ?: "--:--"}",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.displaySmall
                        )

                        SuggestionChip(
                            modifier = Modifier,
                            onClick = {},
                            label = {
                                Row(){
                                    Icon(
                                        painter = painterResource(R.drawable.ic_go_out),
                                        contentDescription = "Go out icon"
                                    )
                                    Text(
                                        text = "--:--:--"
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


    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(
    name = "Light mode preview",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(
    name = "Dark mode preview",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewRegisterContent() {
    JornadaCertaTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) {
            RegisterContent(RegisterUiState())
        }

    }
}
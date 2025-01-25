package com.example.finalucp_113.ui.view.instruktur

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalucp_113.ui.navigation.DestinasiNavigasi
import com.example.finalucp_113.ui.theme.PinkLight
import com.example.finalucp_113.ui.theme.PinkMedium
import com.example.finalucp_113.ui.viewmodel.PenyediaViewModel
import com.example.finalucp_113.ui.viewmodel.instruktur.FormErrorState
import com.example.finalucp_113.ui.viewmodel.instruktur.InsertInstrukturEvent
import com.example.finalucp_113.ui.viewmodel.instruktur.InsertInstrukturUIState
import com.example.finalucp_113.ui.viewmodel.instruktur.InsertInstrukturViewmodel
import kotlinx.coroutines.launch

object DestinasiInsertInstruktur : DestinasiNavigasi {
    override val route = "insert_instruktur"
    override val titleRes = "Insert instruktur"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertInstrukturView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertInstrukturViewmodel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Tambah Instruktur",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Kembali",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = PinkMedium
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            InsertBodyInstruktur(
                insertInstrukturUIState = viewModel.uiState,
                onValueChange = { updateEvent -> viewModel.updateState(updateEvent) },
                onClick = {
                    viewModel.saveData()
                    onNavigate()
                }
            )
        }
    }
}


@Composable
fun InsertBodyInstruktur(
    modifier: Modifier = Modifier,
    onValueChange: (InsertInstrukturEvent) -> Unit,
    insertInstrukturUIState: InsertInstrukturUIState,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = PinkLight) // Light pink background
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Formulir Instruktur",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 16.dp),
                textAlign = TextAlign.Center,
                color = PinkMedium
            )


        }
    }
}

@Composable
fun FormInstruktur(
    insertInstrukturEvent: InsertInstrukturEvent,
    onValueChange: (InsertInstrukturEvent) -> Unit,
    errorState: FormErrorState,
    modifier: Modifier = Modifier
) {

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = insertInstrukturEvent.id_instruktur,
        onValueChange = { onValueChange(insertInstrukturEvent.copy(id_instruktur = it)) },
        label = { Text("ID Instruktur") },
        isError = errorState.id_instruktur != null,  // Tampilkan error jika ada
        placeholder = { Text("Masukkan ID Instruktur") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )

    if (errorState.id_instruktur != null) {
        Text(text = errorState.id_instruktur, color = Color.Red, fontSize = 12.sp)
    }
    Spacer(
        modifier = Modifier.height(16.dp)

    )


    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertInstrukturEvent.nama_instruktur,
            onValueChange = { onValueChange(insertInstrukturEvent.copy(nama_instruktur = it)) },
            label = { Text("Nama") },
            isError = errorState.nama_instruktur != null,
            placeholder = { Text("Masukkan nama") }
        )
        if (errorState.nama_instruktur != null) {
            Text(text = errorState.nama_instruktur ?: "", color = Color.Red, fontSize = 12.sp)
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = insertInstrukturEvent.email,
                onValueChange = { onValueChange(insertInstrukturEvent.copy(email = it)) },
                label = { Text("Email") },
                isError = errorState.email != null,
                placeholder = { Text("Masukkan Email") }
            )
            if (errorState.email != null) {
                Text(text = errorState.email ?: "", color = Color.Red, fontSize = 12.sp)
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Column(modifier = modifier.fillMaxWidth()) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = insertInstrukturEvent.no_telepon,
                    onValueChange = { onValueChange(insertInstrukturEvent.copy(no_telepon = it)) },
                    label = { Text("No Telepon") },
                    isError = errorState.no_telepon != null,
                    placeholder = { Text("Masukkan No Telepon") }
                )
                if (errorState.no_telepon != null) {
                    Text(text = errorState.no_telepon ?: "", color = Color.Red, fontSize = 12.sp)
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )
            }

            Column(modifier = modifier.fillMaxWidth()) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = insertInstrukturEvent.deskripsi,
                    onValueChange = { onValueChange(insertInstrukturEvent.copy(deskripsi = it)) },
                    label = { Text("Deskripsi") },
                    isError = errorState.deskripsi != null,
                    placeholder = { Text("Masukkan No Telepon") }
                )
                if (errorState.deskripsi != null) {
                    Text(text = errorState.deskripsi ?: "", color = Color.Red, fontSize = 12.sp)
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )
            }

        }
    }
}

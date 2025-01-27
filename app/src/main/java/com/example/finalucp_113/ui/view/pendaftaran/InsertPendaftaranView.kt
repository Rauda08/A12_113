package com.example.finalucp_113.ui.view.pendaftaran

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
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.finalucp_113.ui.dropdown.DynamicSelectedTextField
import com.example.finalucp_113.ui.navigation.DestinasiNavigasi
import com.example.finalucp_113.ui.theme.Pink80
import com.example.finalucp_113.ui.theme.PinkLight
import com.example.finalucp_113.ui.theme.PinkMedium
import com.example.finalucp_113.ui.viewmodel.kursus.HomeKursusViewModel
import com.example.finalucp_113.ui.viewmodel.kursus.KursusPenyediaViewModel
import com.example.finalucp_113.ui.viewmodel.kursus.KursusUiState
import com.example.finalucp_113.ui.viewmodel.pendaftaran.FormErrorState
import com.example.finalucp_113.ui.viewmodel.pendaftaran.InsertPendaftaranEvent
import com.example.finalucp_113.ui.viewmodel.pendaftaran.InsertPendaftaranUIState
import com.example.finalucp_113.ui.viewmodel.pendaftaran.InsertPendaftaranViewModel
import com.example.finalucp_113.ui.viewmodel.pendaftaran.PendaftaranPenyediaViewModel
import com.example.finalucp_113.ui.viewmodel.siswa.HomeSiswaViewModel
import com.example.finalucp_113.ui.viewmodel.siswa.SiswaPenyediaViewModel
import com.example.finalucp_113.ui.viewmodel.siswa.SiswaUiState
import kotlinx.coroutines.launch

object DestinasiInsertPendaftaran : DestinasiNavigasi {
    override val route = "insert_pendaftaran"
    override val titleRes = "Insert pendaftaran"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertPendaftaranView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPendaftaranViewModel = viewModel(factory = PendaftaranPenyediaViewModel.Factory),
    viewModelSiswa: HomeSiswaViewModel = viewModel(factory = SiswaPenyediaViewModel.Factory),
    viewModelKursus: HomeKursusViewModel = viewModel(factory = KursusPenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val siswaUiState = viewModelSiswa.siswaUIState
    val kursusUiState = viewModelKursus.kursusUIState

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
                        text = "Tambah Pendaftaran",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFF46051C),
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Kembali",
                            tint = Color(0xFF46051C)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Pink80
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFF46051C))
        ) {
            InsertBodyPendaftaran(
                insertPendaftaranUIState = uiState,
                onValueChange = { updateEvent -> viewModel.updateState(updateEvent) },
                onClick = {
                    viewModel.saveData()
                    onNavigate()
                },
            )
        }
    }
}

@Composable
fun InsertBodyPendaftaran(
    modifier: Modifier = Modifier,
    onValueChange: (InsertPendaftaranEvent) -> Unit,
    insertPendaftaranUIState: InsertPendaftaranUIState,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(30.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Pink80)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Formulir Pendaftaran",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 16.dp),
                textAlign = TextAlign.Center,
                color = PinkMedium
            )

            FormPendaftaran(
                insertPendaftaranEvent = insertPendaftaranUIState.pendaftaranEvent,
                onValueChange = onValueChange,
                errorState = insertPendaftaranUIState.isEntryValid,
                viewModelSiswa = viewModel(),
                viewModelKursus = viewModel()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = PinkMedium)
            ) {
                Text(text = "Simpan", color = Color.White)
            }
        }
    }
}

@Composable
fun FormPendaftaran(
    insertPendaftaranEvent: InsertPendaftaranEvent,
    onValueChange: (InsertPendaftaranEvent) -> Unit,
    errorState: FormErrorState,
    viewModelSiswa: HomeSiswaViewModel,
    viewModelKursus: HomeKursusViewModel,
    modifier: Modifier = Modifier
) {

    val siswaUiState = viewModelSiswa.siswaUIState
    val kursusUiState = viewModelKursus.kursusUIState

    when (siswaUiState) {
        is SiswaUiState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        is SiswaUiState.Error -> {
            Text("Gagal mengambil data siswa", color = MaterialTheme.colorScheme.error)
        }

        is SiswaUiState.Success -> {
            val siswaList = siswaUiState.siswa
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = insertPendaftaranEvent.id_pendaftaran,
                    onValueChange = { onValueChange(insertPendaftaranEvent.copy(id_pendaftaran = it)) },
                    label = { Text("ID Pendaftaran") },
                    isError = errorState.id_pendaftaran != null,
                    placeholder = { Text("Masukkan ID Pendaftaran") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                if (errorState.id_pendaftaran != null) {
                    Text(text = errorState.id_pendaftaran, color = Color.Red, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(16.dp)
                )

                DynamicSelectedTextField(
                    selectedValue = insertPendaftaranEvent.id_siswa.toString(),
                    options = siswaList.map { it.id_siswa.toString() },
                    label = "Pilih ID Siswa",
                    onValueChangedEvent = { selectedId: String ->
                        onValueChange(insertPendaftaranEvent.copy(id_siswa = selectedId))
                    }
                )
            }
        }
    }

    when (kursusUiState) {
        is KursusUiState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        is KursusUiState.Error -> {
            Text("Gagal mengambil data Kursus", color = MaterialTheme.colorScheme.error)
        }

        is KursusUiState.Success -> {
            val kursusList = kursusUiState.kursus
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DynamicSelectedTextField(
                    selectedValue = insertPendaftaranEvent.id_kursus.toString(),
                    options = kursusList.map { it.id_kursus.toString() },
                    label = "Pilih ID Kursus",
                    onValueChangedEvent = { selectedId: String ->
                        onValueChange(insertPendaftaranEvent.copy(id_kursus = selectedId))
                    }
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = insertPendaftaranEvent.tanggal_pendaftaran,
                    onValueChange = { onValueChange(insertPendaftaranEvent.copy(tanggal_pendaftaran = it)) },
                    label = { Text("Tanggal Pendaftaran") },
                    isError = errorState.tanggal_pendaftaran != null,
                    placeholder = { Text("Masukkan Tanggal Pendaftaran") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                if (errorState.tanggal_pendaftaran != null) {
                    Text(text = errorState.tanggal_pendaftaran, color = Color.Red, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

package com.example.finalucp_113.ui.view.kursus

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalucp_113.model.Kursus
import com.example.finalucp_113.ui.customwidget.CostumeTopAppBar
import com.example.finalucp_113.ui.navigation.DestinasiNavigasi
import com.example.finalucp_113.ui.viewmodel.kursus.HomeKursusViewModel
import com.example.finalucp_113.ui.viewmodel.kursus.KursusUiState
import com.example.finalucp_113.ui.viewmodel.kursus.PenyediaViewModel

object DestinasiHomeKursus : DestinasiNavigasi {
    override val route = "home_kursus"
    override val titleRes = "Home Kursus"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeKursusView(
    modifier: Modifier = Modifier,
    onAddKursus: () -> Unit = {},
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeKursusViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = "Home Data kursus",
                scrollBehavior = scrollBehavior,
                canNavigateBack = false,
                onRefresh = {
                    viewModel.getKursus()
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddKursus,
                containerColor = Color(0xFF46051C),
                contentColor = Color.White,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Data Kursus"
                )
            }
        }
    ) { innerPadding ->
        KursusStatus(
            kursusUiState = viewModel.kursusUIState,
            retryAction = { viewModel.getKursus() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deletekursus(it.id_kursus)
                viewModel.getKursus()
            }
        )
    }
}

@Composable
fun KursusStatus(
    kursusUiState: KursusUiState,
    modifier: Modifier = Modifier,
    retryAction :() -> Unit,
    onDeleteClick: (Kursus) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (kursusUiState) {
        is KursusUiState.Success -> {
            if (kursusUiState.kursus.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data kursus")
                }
            } else {
                KursusLayout(
                    kursus = kursusUiState.kursus,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_kursus.toString()) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        else -> Unit
    }
}

@Composable
fun KursusLayout(
    kursus: List<Kursus>,
    modifier: Modifier = Modifier,
    onDetailClick: (Kursus) -> Unit,
    onDeleteClick: (Kursus) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(kursus) { kursus ->
            com.example.finalucp_113.ui.view.kursus.KursusCard(
                kursus = kursus,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(kursus) },
                onDeleteClick = {
                    onDeleteClick(kursus)
                }
            )
        }
    }
}

@Composable
fun KursusCard(
    kursus: Kursus,
    modifier: Modifier = Modifier,
    onDeleteClick: (Kursus) -> Unit = {}
) {

    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.padding(horizontal = 8.dp, vertical = 3.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFC1E3)), // Warna latar belakang lembut
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = kursus.nama_kursus,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 25.sp),
                    color = Color(0xFF46051C)
                )
                Text(
                    text = kursus.harga,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                    color = Color(0xFF46051C)
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Id Kursus: ${kursus.id_kursus}",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                    color = Color(0xFF46051C)
                )
                Text(
                    text = "Id Instruktur: ${kursus.id_instruktur}",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                    color = Color(0xFF46051C)
                )
                Text(
                    text = "Kategori: ${kursus.kategori}",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                    color = Color(0xFF46051C)
                )
                Text(
                    text = "Deskripsi: ${kursus.deskripsi}",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                    color = Color(0xFF46051C)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { showDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Hapus kursus",
                        tint = Color(0xFF4D1414)
                    )
                }
            }
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Konfirmasi Hapus") },
            text = { Text("Apakah Anda yakin ingin menghapus data kursus ini?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteClick(kursus)
                        showDialog = false
                    }
                ) {
                    Text("Hapus")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text("Batal")
                }
            },

            )
    }
}
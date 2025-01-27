package com.example.finalucp_113.ui.view.pendaftaran

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalucp_113.model.Pendaftaran
import com.example.finalucp_113.ui.customwidget.CostumeTopAppBar
import com.example.finalucp_113.ui.navigation.DestinasiNavigasi
import com.example.finalucp_113.ui.viewmodel.pendaftaran.HomePendaftaranViewModel
import com.example.finalucp_113.ui.viewmodel.pendaftaran.PendaftaranPenyediaViewModel
import com.example.finalucp_113.ui.viewmodel.pendaftaran.PendaftaranUiState

object DestinasiHomePendaftaran : DestinasiNavigasi {
    override val route = "home_pendaftaran"
    override val titleRes = "Home Pendaftaran"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePendaftaranView(
    modifier: Modifier = Modifier,
    onAddPendaftaran: () -> Unit = {},
    onDetailClick: (String) -> Unit = {},
    viewModel: HomePendaftaranViewModel = viewModel(factory = PendaftaranPenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = "Home Data Pendaftaran",
                scrollBehavior = scrollBehavior,
                canNavigateBack = false,
                onRefresh = {
                    viewModel.getpendaftaran()
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddPendaftaran,
                containerColor = Color(0xFF46051C),
                contentColor = Color.White,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Data Pendaftaran"
                )
            }
        }
    ) { innerPadding ->
        PendaftaranStatus(
            pendaftaranUiState = viewModel.pendaftaranUIState,
            retryAction = { viewModel.getpendaftaran() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deletependaftaran(it.id_pendaftaran)
                viewModel.getpendaftaran()
            }
        )
    }
}

@Composable
fun PendaftaranStatus(
    pendaftaranUiState: PendaftaranUiState,
    modifier: Modifier = Modifier,
    retryAction :() -> Unit,
    onDeleteClick: (Pendaftaran) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (pendaftaranUiState) {
        is PendaftaranUiState.Success -> {
            if (pendaftaranUiState.pendaftaran.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Pendaftaran")
                }
            } else {
                PendaftaranLayout(
                    pendaftaran = pendaftaranUiState.pendaftaran,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_pendaftaran.toString()) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        else -> Unit
    }
}

@Composable
fun PendaftaranLayout(
    pendaftaran: List<Pendaftaran>,
    modifier: Modifier = Modifier,
    onDetailClick: (Pendaftaran) -> Unit,
    onDeleteClick: (Pendaftaran) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pendaftaran) { pendaftaran ->
            PendaftaranCard(
                pendaftaran = pendaftaran,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(pendaftaran) },
                onDeleteClick = {
                    onDeleteClick(pendaftaran)
                }
            )
        }
    }
}

@Composable
fun PendaftaranCard(
    pendaftaran: Pendaftaran,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pendaftaran) -> Unit = {}
) {

    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.padding(horizontal = 8.dp, vertical = 3.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFDD5E3)),
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
                    text = pendaftaran.id_siswa,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 25.sp),
                    color = Color(0xFF46051C)
                )
                Text(
                    text = pendaftaran.id_kursus,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                    color = Color(0xFF46051C)
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "ID: ${pendaftaran.id_pendaftaran}",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                    color = Color(0xFF46051C)
                )
                Text(
                    text = "Tanggal: ${pendaftaran.tanggal_pendaftaran}",
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
                        contentDescription = "Hapus Pendaftaran",
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
            text = { Text("Apakah Anda yakin ingin menghapus data pendaftaran ini?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteClick(pendaftaran)
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

@Preview(showBackground = true)
@Composable
fun PreviewPendaftaranCard() {
    PendaftaranCard(
        pendaftaran = Pendaftaran(
            id_pendaftaran = "1",
            id_siswa = "SIS001",
            id_kursus = "KRS001",
            tanggal_pendaftaran = "2025-01-27"
        ),
        onDeleteClick = {}
    )
}

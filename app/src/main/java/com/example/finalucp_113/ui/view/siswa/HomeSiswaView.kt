package com.example.finalucp_113.ui.view.siswa

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalucp_113.R
import com.example.finalucp_113.model.Siswa
import com.example.finalucp_113.ui.customwidget.CostumeTopAppBar
import com.example.finalucp_113.ui.navigation.DestinasiNavigasi
import com.example.finalucp_113.ui.viewmodel.siswa.HomeSiswaViewModel
import com.example.finalucp_113.ui.viewmodel.siswa.SiswaPenyediaViewModel
import com.example.finalucp_113.ui.viewmodel.siswa.SiswaUiState


object DestinasiHomeSiswa : DestinasiNavigasi {
    override val route = "home_siswa"
    override val titleRes = "Home Siswa"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSiswaView(
    modifier: Modifier = Modifier,
    onAddSiswa: () -> Unit = {},
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeSiswaViewModel = viewModel(factory = SiswaPenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
                CostumeTopAppBar(
                    title = "Home Data Siswa",
                    scrollBehavior = scrollBehavior,
                    canNavigateBack = false,
                    onRefresh = {
                        viewModel.getSiswa()
                    }
                )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddSiswa,
                containerColor = Color(0xFF46051C),
                contentColor = Color.White,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Data Siswa"
                )
            }
        }
    ) { innerPadding ->
        SiswaStatus(
            siswaUiState = viewModel.siswaUIState,
            retryAction = { viewModel.getSiswa() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deletesiswa(it.id_siswa)
                viewModel.getSiswa()
            }
        )
    }
}

@Composable
fun SiswaStatus(
    siswaUiState: SiswaUiState,
    modifier: Modifier = Modifier,
    retryAction :() -> Unit,
    onDeleteClick: (Siswa) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (siswaUiState) {
        is SiswaUiState.Success -> {
            if (siswaUiState.siswa.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Siswa")
                }
            } else {
                SiswaLayout(
                    siswa = siswaUiState.siswa,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_siswa.toString()) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        else -> Unit
    }
}

@Composable
fun SiswaLayout(
    siswa: List<Siswa>,
    modifier: Modifier = Modifier,
    onDetailClick: (Siswa) -> Unit,
    onDeleteClick: (Siswa) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(siswa) { siswa ->
            SiswaCard(
                siswa = siswa,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(siswa) },
                onDeleteClick = {
                    onDeleteClick(siswa)
                }
            )
        }
    }
}

@Composable
fun SiswaCard(
    siswa: Siswa,
    modifier: Modifier = Modifier,
    onDeleteClick: (Siswa) -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .padding(8.dp)
            .border(1.dp, Color(0xFF46051C), MaterialTheme.shapes.medium), // Border untuk Card
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE6F0)), // Warna latar belakang lembut
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = R.drawable.orangg),
                contentDescription = "Gambar orang",
                modifier = Modifier
                    .size(100.dp) // Ukuran gambar
                    .padding(end = 16.dp)
            )

            // Informasi siswa di kanan
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InfoRow(Icons.Default.AccountBox, siswa.id_siswa)
                InfoRow(Icons.Default.Person, siswa.nama_siswa)
                InfoRow(Icons.Default.Email, siswa.email)
                InfoRow(Icons.Default.Phone, siswa.no_telepon)
            }

            // Tombol hapus
            IconButton(onClick = { showDialog = true }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Hapus siswa",
                    tint = Color(0xFF46051C)
                )
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Konfirmasi Hapus") },
            text = { Text("Apakah Anda yakin ingin menghapus data siswa ini?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteClick(siswa)
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
            }
        )
    }
}

@Composable
fun InfoRow(icon: ImageVector, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = "Icon",
            tint = Color(0xFF46051C),
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
            color = Color(0xFF46051C),
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
package com.example.finalucp_113.ui.view.siswa

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalucp_113.model.Siswa
import com.example.finalucp_113.ui.customwidget.CostumeTopAppBar
import com.example.finalucp_113.ui.navigation.DestinasiNavigasi
import com.example.finalucp_113.ui.viewmodel.PenyediaViewModel
import com.example.finalucp_113.ui.viewmodel.siswa.HomeSiswaViewModel
import com.example.finalucp_113.ui.viewmodel.siswa.SiswaUiState


object DestinasiHomeSiswa : DestinasiNavigasi {
    override val route = "home_siswa"
    override val titleRes = "Home Acara"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSiswaView(
    modifier: Modifier = Modifier,
    onAddSiswa: () -> Unit = {},
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeSiswaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
                CostumeTopAppBar(
                    title = "Home Siswa",
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
                containerColor = Color(0xFFF66596),
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
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = siswa.id_siswa,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = siswa.nama_siswa,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(siswa) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
                Text(
                    text = siswa.email,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Text(
                text = siswa.no_telepon,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

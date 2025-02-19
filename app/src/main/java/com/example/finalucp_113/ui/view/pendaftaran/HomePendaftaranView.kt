package com.example.finalucp_113.ui.view.pendaftaran

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalucp_113.R
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
                containerColor = Color(0xFFFDD5E3),
                contentColor = Color(0xFF46051C),
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Data Pendaftaran"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFF46051C)),
        ){

            FilterAndSearch(viewModel)

            PendaftaranStatus(
                pendaftaranUiState = viewModel.pendaftaranUIState,
                retryAction = { viewModel.getpendaftaran() },
                onDetailClick = onDetailClick,
                onDeleteClick = {
                    viewModel.deletependaftaran(it.id_pendaftaran)
                    viewModel.getpendaftaran()
                }
            )
        }
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
                    Text(text = "Tidak ada data Pendaftaran",
                        color = Color(0xFFFDD5E3))
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
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
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

    val kategoriIcon = when (pendaftaran.kategori) {
        "Saintek" -> R.drawable.sigma
        "Soshum" -> R.drawable.buku
        else -> R.drawable.orangg
    }

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
                // Icon di kiri atas
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = kategoriIcon),
                        contentDescription = "Kategori Ikon",
                        tint = Color(0xFF46051C),
                        modifier = Modifier.size(32.dp).padding(end = 8.dp)
                    )

                    Text(
                        text = pendaftaran.nama_siswa,
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                        color = Color(0xFF46051C)
                    )
                }

                Text(
                    text = pendaftaran.kategori,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                    color = Color(0xFF46051C)
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
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
                Text(
                    text = pendaftaran.nama_kursus,
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

    // Dialog konfirmasi hapus
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

@Composable
fun FilterAndSearch(viewModel: HomePendaftaranViewModel) {
    var selectedCategory by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(selectedCategory, searchQuery) {
        viewModel.filterPendaftaran(category = selectedCategory, query = searchQuery)
    }

    Column {
        // Filter Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val categories = listOf("Saintek", "Soshum", "Semua")
            categories.forEach { category ->
                TextButton(
                    onClick = {
                        selectedCategory = if (category == "Semua") "" else category
                    },
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = if (selectedCategory == category || (category == "Semua" && selectedCategory.isEmpty())) {
                                Color(0xFF46051C)
                            } else Color(0xFFFDD5E3),
                            shape = MaterialTheme.shapes.medium
                        )
                        .background(
                            color = if (selectedCategory == category || (category == "Semua" && selectedCategory.isEmpty())) {
                                Color(0xFFFDD5E3)
                            } else Color(0xFF46051C),
                            shape = MaterialTheme.shapes.medium
                        ),
                ) {
                    Text(
                        text = category,
                        color = if (selectedCategory == category || (category == "Semua" && selectedCategory.isEmpty())) {
                            Color(0xFF46051C)
                        } else Color(0xFFFDD5E3),
                    )
                }
            }
        }

        SearchPendafataran(viewModel = viewModel, query = searchQuery) { newQuery ->
        searchQuery = newQuery
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPendafataran(
    viewModel: HomePendaftaranViewModel,
    query: String,
    onQueryChange: (String) -> Unit
) {
    TextField(
        value = query,
        onValueChange = {
            onQueryChange(it)
        },
        placeholder = {
            Text(
                "Cari Pendaftar...",
                color = Color(0xFF46051C)
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color(0xFF46051C)
            )
        },
        colors = androidx.compose.material3.TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFFFDD5E3),
            focusedIndicatorColor = Color(0xFF46051C),
            unfocusedIndicatorColor = Color(0xFFBDBDBD),
            cursorColor = Color(0xFF46051C)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        singleLine = true
    )
}
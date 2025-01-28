package com.example.finalucp_113.ui.view.kursus

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalucp_113.R
import com.example.finalucp_113.model.Kursus
import com.example.finalucp_113.ui.customwidget.CostumeTopAppBar
import com.example.finalucp_113.ui.navigation.DestinasiNavigasi
import com.example.finalucp_113.ui.viewmodel.kursus.HomeKursusViewModel
import com.example.finalucp_113.ui.viewmodel.kursus.KursusPenyediaViewModel
import com.example.finalucp_113.ui.viewmodel.kursus.KursusUiState
import kotlinx.coroutines.delay

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
    viewModel: HomeKursusViewModel = viewModel(factory = KursusPenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize()
            .background(Color(0xFF46051C)),
        topBar = {
            CostumeTopAppBar(
                title = "Home Data Kursus",
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
                containerColor = Color(0xFFFDD5E3),
                contentColor = Color(0xFF46051C),
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Data Kursus"
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

            SearchKursus(viewModel)

            KursusStatus(
                kursusUiState = viewModel.kursusUIState,
                retryAction = { viewModel.getKursus() },
                onDetailClick = onDetailClick,
                onDeleteClick = {
                    viewModel.deletekursus(it.id_kursus)
                    viewModel.getKursus()
                },
            )
        }
    }
}


@Composable
fun KursusStatus(
    kursusUiState: KursusUiState,
    modifier: Modifier = Modifier,
    retryAction: () -> Unit,
    onDeleteClick: (Kursus) -> Unit = {},
    onDetailClick: (String) -> Unit,
) {
    when (kursusUiState) {
        is KursusUiState.Success -> {
            if (kursusUiState.kursus.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data kursus", style = MaterialTheme.typography.bodyLarge)
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
        KursusUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        KursusUiState.Error -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Terjadi kesalahan. Coba lagi.", style = MaterialTheme.typography.bodyLarge)
            }
        }
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
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(kursus) { kursus ->
            KursusCard(
                kursus = kursus,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(kursus) },
                onDeleteClick = { onDeleteClick(kursus) }
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
    val categoryIcon = when (kursus.kategori) {
        "Saintek" -> R.drawable.sigma
        "Soshum" -> R.drawable.buku
        else -> R.drawable.orangg
    }

    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.padding(horizontal = 8.dp, vertical = 4.dp).fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFDD5E3)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp) // Mengatur jarak antar elemen
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = categoryIcon),
                    contentDescription = "Kategori Ikon",
                    tint = Color(0xFF46051C),
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = kursus.nama_kursus,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
                    color = Color(0xFF46051C)
                )
            }

            // Mengatur informasi tambahan dan tombol delete dalam Column
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                // Menampilkan Id Kursus
                Text(
                    text = "Id Kursus: ${kursus.id_kursus}",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                    color = Color(0xFF46051C)
                )
                // Menampilkan Kategori
                Text(
                    text = "Kategori: ${kursus.kategori}",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                    color = Color(0xFF46051C)
                )
                // Menampilkan Harga
                Text(
                    text = "Harga: ${kursus.harga}",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                    color = Color(0xFF46051C)
                )

                // Row untuk Id Instruktur dan tombol delete
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Id Instruktur: ${kursus.id_instruktur}",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                        color = Color(0xFF46051C)
                    )
                    IconButton(onClick = { showDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Hapus kursus",
                            tint = Color(0xFF46051C)
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Konfirmasi Hapus", style = MaterialTheme.typography.titleMedium) },
            text = { Text("Apakah Anda yakin ingin menghapus data kursus ini?", style = MaterialTheme.typography.bodyMedium) },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteClick(kursus)
                        showDialog = false
                    }
                ) {
                    Text("Hapus", color = Color(0xFF46051C))
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchKursus(viewModel: HomeKursusViewModel) {
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(searchQuery) {
        delay(200)
        viewModel.searchKursus(searchQuery)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Cari kursus...") },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFFDD5E3),
                    shape = MaterialTheme.shapes.medium
                )
                .border(
                    width = 2.dp,
                    color = Color(0xFF46051C),
                    shape = MaterialTheme.shapes.medium
                ),
            textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color(0xFF46051C)
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = {
                        searchQuery = "" // Reset pencarian
                        viewModel.searchKursus("")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear Search",
                            tint = Color(0xFF46051C) // Warna ikon yang senada
                        )
                    }
                }
            },
            colors = androidx.compose.material3.TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color(0xFF46051C)
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewKursusCard() {
    val dummyKursus = Kursus(
        id_kursus = "1",
        nama_kursus = "Belajar Pemrograman Kotlin",
        id_instruktur = "101",
        kategori = "Saintek",
        deskripsi = "Kursus pemrograman Kotlin untuk pemula hingga mahir.",
        harga = "Rp 500.000",
    )

    KursusCard(
        kursus = dummyKursus,
        onDeleteClick = {},
        modifier = Modifier.padding(8.dp)
    )
}


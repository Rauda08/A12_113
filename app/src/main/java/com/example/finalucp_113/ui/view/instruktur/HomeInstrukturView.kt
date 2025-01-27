package com.example.finalucp_113.ui.view.instruktur

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalucp_113.R
import com.example.finalucp_113.model.Instruktur
import com.example.finalucp_113.ui.customwidget.CostumeTopAppBar
import com.example.finalucp_113.ui.navigation.DestinasiNavigasi
import com.example.finalucp_113.ui.viewmodel.instruktur.HomeInstrukturViewModel
import com.example.finalucp_113.ui.viewmodel.instruktur.InstrukturPenyediaViewModel
import com.example.finalucp_113.ui.viewmodel.instruktur.InstrukturUiState

object DestinasiHomeInstruktur : DestinasiNavigasi {
    override val route = "home_instruktur"
    override val titleRes = "Home Instruktur"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeInstrukturView(
    modifier: Modifier = Modifier,
    onAddInstruktur: () -> Unit = {},
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeInstrukturViewModel = viewModel(factory = InstrukturPenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = "Home Data Instruktur",
                scrollBehavior = scrollBehavior,
                canNavigateBack = false,
                onRefresh = {
                    viewModel.getInstruktur()
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddInstruktur,
                containerColor = Color(0xFFFFE6F0),
                contentColor = Color(0xFF46051C),
                modifier = Modifier.padding(16.dp).border(2.dp,Color(0xFF46051C),MaterialTheme.shapes.medium),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Data Instruktur"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().background(Color(0xFF46051C)),
        ) {

            InstrukturStatus(
                instrukturUiState = viewModel.instrukturUiState,
                retryAction = { viewModel.getInstruktur() },
                modifier = Modifier.padding(innerPadding),
                onDetailClick = onDetailClick,
                onDeleteClick = {
                    viewModel.deleteinstruktur(it.id_instruktur)
                    viewModel.getInstruktur()
                }
            )
        }
    }
}

@Composable
fun InstrukturStatus(
    instrukturUiState: InstrukturUiState,
    modifier: Modifier = Modifier,
    retryAction :() -> Unit,
    onDeleteClick: (Instruktur) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (instrukturUiState) {
        is InstrukturUiState.Success -> {
            if (instrukturUiState.instruktur.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Instruktur")
                }
            } else {
                InstrukturLayout(
                    instruktur = instrukturUiState.instruktur,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_instruktur.toString()) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        else -> Unit
    }
}

@Composable
fun InstrukturLayout(
    instruktur: List<Instruktur>,
    modifier: Modifier = Modifier,
    onDetailClick: (Instruktur) -> Unit,
    onDeleteClick: (Instruktur) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(instruktur) { instruktur ->
            InstrukturCard(
                instruktur = instruktur,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(instruktur) },
                onDeleteClick = {
                    onDeleteClick(instruktur)
                }
            )
        }
    }
}

@Composable
fun InstrukturCard(
    instruktur: Instruktur,
    modifier: Modifier = Modifier,
    onDeleteClick: (Instruktur) -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .padding(8.dp)
            .border(1.dp, Color(0xFF46051C), MaterialTheme.shapes.medium),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE6F0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Baris untuk ID dan Email
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ID: ${instruktur.id_instruktur}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold // Membuat teks bold
                    ),
                    color = Color(0xFF46051C),
                    modifier = Modifier.weight(1f) // Membuat ID tetap di kiri
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Ikon Email",
                        tint = Color(0xFF46051C),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = instruktur.email,
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                        color = Color(0xFF46051C),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.instruktur),
                    contentDescription = "Gambar orang",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(end = 16.dp)
                )

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    InfoRow(Icons.Default.Person, instruktur.nama_instruktur)
                    InfoRow(Icons.Default.Phone, instruktur.no_telepon)
                    InfoRow(Icons.Default.Favorite, instruktur.deskripsi)
                }

                // Tombol hapus
                IconButton(onClick = { showDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Hapus Data Instruktur",
                        tint = Color(0xFF46051C)
                    )
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Konfirmasi Hapus") },
            text = { Text("Apakah Anda yakin ingin menghapus data instruktur ini?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteClick(instruktur)
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


@Preview(showBackground = true)
@Composable
fun InstrukturCardPreview() {
    val dummyInstruktur = Instruktur(
        id_instruktur = "Inst01",
        nama_instruktur = "John Doe",
        email = "johndoe@example.com",
        no_telepon = "081234567890",
        deskripsi = "Instruktur profesional dengan pengalaman 5 tahun."
    )

    InstrukturCard(
        instruktur = dummyInstruktur,
        modifier = Modifier.fillMaxWidth(),
        onDeleteClick = {}
    )
}
package com.example.finalucp_113.ui.view.instruktur

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalucp_113.model.Instruktur
import com.example.finalucp_113.ui.customwidget.CostumeTopAppBar
import com.example.finalucp_113.ui.navigation.DestinasiNavigasi
import com.example.finalucp_113.ui.viewmodel.instruktur.DetailInstrukturUiState
import com.example.finalucp_113.ui.viewmodel.instruktur.DetailInstrukturViewModel
import com.example.finalucp_113.ui.viewmodel.instruktur.InstrukturPenyediaViewModel

object DestinasiDetailInstruktur : DestinasiNavigasi {
    override val route = "id_detail"
    override val titleRes = "Detail Instruktur"
    const val id_instruktur = "id_instruktur"
    val routeWithArgs = "$route/{$id_instruktur}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailInstrukturView(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onEditClick: (String) -> Unit,
    detailViewModel: DetailInstrukturViewModel = viewModel(factory = InstrukturPenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailInstruktur.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val id_instruktur = (detailViewModel.detailInstrukturUiState as? DetailInstrukturUiState.Success)?.instruktur?.id_instruktur
                    if (id_instruktur != null) onEditClick(id_instruktur)
                },
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Instruktur",
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding).offset(y = (-70).dp)
        ) {
            DetailStatus(
                InstrukturUiState = detailViewModel.detailInstrukturUiState,
                retryAction = { detailViewModel.getinstrukturbyId() },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun DetailStatus(
    InstrukturUiState: DetailInstrukturUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (InstrukturUiState) {
        is DetailInstrukturUiState.Success -> {
            DetailCard(
                instruktur = InstrukturUiState.instruktur,
                modifier = modifier.padding(16.dp)
            )
        }

        is DetailInstrukturUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is DetailInstrukturUiState.Error -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Terjadi kesalahan.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = retryAction) {
                        Text(text = "Coba Lagi")
                    }
                }
            }
        }
    }
}

@Composable
fun DetailCard(
    instruktur: Instruktur,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ComponentDetailInstruktur(judul = "Id Instruktur", isinya = instruktur.id_instruktur)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailInstruktur(judul = "Nama Instruktur", isinya = instruktur.nama_instruktur)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailInstruktur(judul = "Email", isinya = instruktur.email)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailInstruktur(judul = "No Telepon", isinya = instruktur.no_telepon)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailInstruktur(judul = "Deskripsi", isinya = instruktur.deskripsi)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ComponentDetailInstruktur(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$judul:",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}
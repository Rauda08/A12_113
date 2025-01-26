package com.example.finalucp_113.ui.view.pendaftaran

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
import com.example.finalucp_113.model.Pendaftaran
import com.example.finalucp_113.ui.customwidget.CostumeTopAppBar
import com.example.finalucp_113.ui.navigation.DestinasiNavigasi
import com.example.finalucp_113.ui.viewmodel.pendaftaran.DetailpendaftaranUiState
import com.example.finalucp_113.ui.viewmodel.pendaftaran.DetailpendaftaranViewModel
import com.example.finalucp_113.ui.viewmodel.pendaftaran.PendaftaranPenyediaViewModel

object DestinasiDetailPendaftaran : DestinasiNavigasi {
    override val route = "detail_pendaftaran"
    override val titleRes = "Detail Pendaftaran"
    const val id_pendaftaran = "id_pendaftaran"
    val routeWithArgs = "$route/{$id_pendaftaran}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPendaftaranView(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onEditClick: (String) -> Unit,
    detailViewModel: DetailpendaftaranViewModel = viewModel(factory = PendaftaranPenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailPendaftaran.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val id_pendaftaran = (detailViewModel.detailpendaftaranUiState as? DetailpendaftaranUiState.Success)?.pendaftaran?.id_pendaftaran
                    if (id_pendaftaran != null) onEditClick(id_pendaftaran)
                },
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Pendaftaran",
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
                pendaftaranUiState = detailViewModel.detailpendaftaranUiState,
                retryAction = { detailViewModel.getpendaftaranbyId() },
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
    pendaftaranUiState: DetailpendaftaranUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (pendaftaranUiState) {
        is DetailpendaftaranUiState.Success -> {
            DetailCard(
                pendaftaran = pendaftaranUiState.pendaftaran,
                modifier = modifier.padding(16.dp)
            )
        }

        is DetailpendaftaranUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is DetailpendaftaranUiState.Error -> {
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
    pendaftaran: Pendaftaran,
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
            ComponentDetailPendaftaran(judul = "Id Pendaftaran", isinya = pendaftaran.id_pendaftaran)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailPendaftaran(judul = "Id Siswa", isinya = pendaftaran.id_siswa)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailPendaftaran(judul = "Id Kursus", isinya = pendaftaran.id_kursus)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailPendaftaran(judul = "Tanggal Pendaftaran", isinya = pendaftaran.tanggal_pendaftaran)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ComponentDetailPendaftaran(
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


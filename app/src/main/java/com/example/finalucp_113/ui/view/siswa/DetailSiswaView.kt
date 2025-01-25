package com.example.finalucp_113.ui.view.siswa

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
import com.example.finalucp_113.model.Siswa
import com.example.finalucp_113.ui.customwidget.CostumeTopAppBar
import com.example.finalucp_113.ui.navigation.DestinasiNavigasi
import com.example.finalucp_113.ui.viewmodel.PenyediaViewModel
import com.example.finalucp_113.ui.viewmodel.siswa.DetailsiswaUiState
import com.example.finalucp_113.ui.viewmodel.siswa.DetailsiswaViewModel

object DestinasiDetailSiswa : DestinasiNavigasi {
    override val route = "id_detail"
    override val titleRes = "Detail Siswa"
    const val id_siswa = "id_siswa"
    val routeWithArgs = "$route/{$id_siswa}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailSiswaView(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onEditClick: (String) -> Unit,
    detailViewModel: DetailsiswaViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailSiswa.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val id_siswa = (detailViewModel.detailsiswaUiState as? DetailsiswaUiState.Success)?.siswa?.id_siswa
                    if (id_siswa != null) onEditClick(id_siswa)
                },
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit siswa",
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
                siswaUiState = detailViewModel.detailsiswaUiState,
                retryAction = { detailViewModel.getsiswabyId() },
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
    siswaUiState: DetailsiswaUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (siswaUiState) {
        is DetailsiswaUiState.Success -> {
            DetailCard(
                siswa = siswaUiState.siswa,
                modifier = modifier.padding(16.dp)
            )
        }

        is DetailsiswaUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is DetailsiswaUiState.Error -> {
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
    siswa: Siswa,
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
            ComponentDetailSiswa(judul = "Id Siswa", isinya = siswa.id_siswa)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailSiswa(judul = "Nama Siswa", isinya = siswa.nama_siswa)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailSiswa(judul = "Email", isinya = siswa.email)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailSiswa(judul = "No Telepon", isinya = siswa.no_telepon)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ComponentDetailSiswa(
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


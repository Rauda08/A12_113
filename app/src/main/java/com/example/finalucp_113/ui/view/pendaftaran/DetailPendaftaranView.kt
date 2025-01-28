package com.example.finalucp_113.ui.view.pendaftaran

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
) {
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
                    val id_pendaftaran =
                        (detailViewModel.detailpendaftaranUiState as? DetailpendaftaranUiState.Success)?.pendaftaran?.id_pendaftaran
                    if (id_pendaftaran != null) onEditClick(id_pendaftaran)
                },
                shape = MaterialTheme.shapes.medium,
                containerColor = Color(0xFFFFE6F0),
                contentColor = Color(0xFF46051C),
                modifier = Modifier.padding(16.dp)
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
                .fillMaxSize()
                .background(Color(0xFF46051C))
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (0).dp)
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
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth()
            .padding(horizontal = 0.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFDD5E3))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Berikut adalah Rincian Pendaftaran :",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF46051C),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Divider(color = Color(0xFF46051C), thickness = 3.dp)

            // List detail
            ComponentDetailPendaftaran(
                judul = "ID Pendaftaran",
                isinya = pendaftaran.id_pendaftaran,
                icon = Icons.Default.Info
            )
            ComponentDetailPendaftaran(
                judul = "ID Siswa",
                isinya = pendaftaran.id_siswa,
                icon = Icons.Default.Person
            )
            ComponentDetailPendaftaran(
                judul = "ID Kursus",
                isinya = pendaftaran.id_kursus,
                icon = Icons.Default.Place
            )
            ComponentDetailPendaftaran(
                judul = "Nama Siswa",
                isinya = pendaftaran.nama_siswa,
                icon = Icons.Default.Place
            )
            ComponentDetailPendaftaran(
                judul = "Nama Kursus",
                isinya = pendaftaran.nama_kursus,
                icon = Icons.Default.Place
            )
            ComponentDetailPendaftaran(
                judul = "Tanggal Pendaftaran",
                isinya = pendaftaran.tanggal_pendaftaran,
                icon = Icons.Default.DateRange
            )
        }
    }
}

@Composable
fun ComponentDetailPendaftaran(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
    icon: ImageVector
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF46051C),
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = judul,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF46051C)
            )
        }
        Text(
            text = isinya,
            fontSize = 16.sp,
            color = Color(0xFF46051C),
            modifier = Modifier.widthIn(max = 180.dp)
        )
    }
}

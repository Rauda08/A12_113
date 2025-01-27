package com.example.finalucp_113.ui.view.instruktur

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.example.finalucp_113.ui.theme.PinkBackground
import com.example.finalucp_113.ui.theme.PinkLight
import com.example.finalucp_113.ui.theme.PinkMedium
import com.example.finalucp_113.ui.viewmodel.instruktur.DetailInstrukturUiState
import com.example.finalucp_113.ui.viewmodel.instruktur.DetailInstrukturViewModel
import com.example.finalucp_113.ui.viewmodel.instruktur.InstrukturPenyediaViewModel

object DestinasiDetailInstruktur : DestinasiNavigasi {
    override val route = "detail_instruktur"
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
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
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
                shape = MaterialTheme.shapes.medium,
                containerColor = Color(0xFFFFE6F0),
                contentColor = Color(0xFF46051C),
                modifier = Modifier.padding(16.dp)
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
                .fillMaxSize()
                .background(Color(0xFF46051C))
                .padding(innerPadding)
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
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 1.dp)
            .then(Modifier.border(width = 10.dp, color = PinkLight)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = PinkBackground)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "ID : ${instruktur.id_instruktur}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = PinkMedium
                )
            }

            Image(
                painter = painterResource(id = R.drawable.instruktur),
                contentDescription = "Gambar orang",
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 16.dp) // Menambahkan jarak antara gambar dan teks
            )

            Text(
                text = "Nama : ${instruktur.nama_instruktur}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = PinkMedium,
                modifier = Modifier.fillMaxWidth() // Pastikan teks mengisi lebar yang tersedia
            )

            Text(
                text = "Email: ${instruktur.email}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = PinkMedium,
                modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)
            )

            Text(
                text = "No Telepon: ${instruktur.no_telepon}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = PinkMedium,
                modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)
            )

            Text(
                text = "Deskripsi : ${instruktur.deskripsi}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = PinkMedium,
                modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DetailCardPreview() {
    val dummyInstruktur = Instruktur(
        id_instruktur = "Inst01",
        nama_instruktur = "John Doe",
        email = "johndoe@example.com",
        no_telepon = "081234567890",
        deskripsi = "terbaik dia adalah guru matematika terbaik disini. dia sangat pandai dalam hal mengajar"
    )

    DetailCard(
        instruktur = dummyInstruktur,
        modifier = Modifier.padding(16.dp)
    )
}
package com.example.finalucp_113.ui.view.kursus

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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalucp_113.model.Kursus
import com.example.finalucp_113.ui.customwidget.CostumeTopAppBar
import com.example.finalucp_113.ui.navigation.DestinasiNavigasi
import com.example.finalucp_113.ui.viewmodel.kursus.DetailkursusUiState
import com.example.finalucp_113.ui.viewmodel.kursus.DetailkursusViewModel
import com.example.finalucp_113.ui.viewmodel.kursus.KursusPenyediaViewModel

object DestinasiDetailKursus : DestinasiNavigasi {
    override val route = "detail_kursus"
    override val titleRes = "Detail Kursus"
    const val id_kursus = "id_kursus"
    val routeWithArgs = "$route/{$id_kursus}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailkursusView(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onEditClick: (String) -> Unit,
    detailViewModel: DetailkursusViewModel = viewModel(factory = KursusPenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailKursus.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val id_kursus = (detailViewModel.detailkursusUiState as? DetailkursusUiState.Success)?.kursus?.id_kursus
                    if (id_kursus != null) onEditClick(id_kursus)
                },
                shape = MaterialTheme.shapes.medium ,
                containerColor = Color(0xFFFFE6F0),
                contentColor = Color(0xFF46051C),
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit kursus",
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
                    .offset(y = (-70).dp)
            ) {
                DetailStatus(
                    kursusUiState = detailViewModel.detailkursusUiState,
                    retryAction = { detailViewModel.getkursusbyId() },
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
    kursusUiState: DetailkursusUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (kursusUiState) {
        is DetailkursusUiState.Success -> {
            DetailCard(
                kursus = kursusUiState.kursus,
                modifier = modifier.padding(16.dp).offset(y = 50.dp)
            )
        }

        is DetailkursusUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is DetailkursusUiState.Error -> {
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
    kursus: Kursus,
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
                text = "Berikut adalah Rincian Kursus :",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF46051C),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Divider(color = Color(0xFF46051C), thickness = 3.dp)

            // List detail
            ComponentDetailKursus(judul = "ID Kursus", isinya = kursus.id_kursus, icon = Icons.Default.Info)
            ComponentDetailKursus(judul = "Nama Kursus", isinya = kursus.nama_kursus, icon = Icons.Default.Place)
            ComponentDetailKursus(judul = "Kategori", isinya = kursus.kategori, icon = Icons.Default.Star)
            ComponentDetailKursus(judul = "Harga", isinya = kursus.harga, icon = Icons.Default.ShoppingCart)
            ComponentDetailKursus(judul = "ID Instruktur", isinya = kursus.id_instruktur, icon = Icons.Default.Person)
            ComponentDetailKursus(judul = "Deskripsi", isinya = kursus.deskripsi, icon = Icons.Default.Edit)
        }
    }
}

@Composable
fun ComponentDetailKursus(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
    icon: ImageVector // Ikon tambahan
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
                modifier = Modifier.size(24.dp).padding(end = 8.dp)
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
            fontSize = 14.sp,
            color = Color(0xFF46051C),
            modifier = Modifier.widthIn(max = 180.dp)
        )
    }
}

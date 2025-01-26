package com.example.finalucp_113.ui.view.pendaftaran

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalucp_113.ui.customwidget.CostumeTopAppBar
import com.example.finalucp_113.ui.navigation.DestinasiNavigasi
import com.example.finalucp_113.ui.viewmodel.kursus.HomeKursusViewModel
import com.example.finalucp_113.ui.viewmodel.kursus.KursusPenyediaViewModel
import com.example.finalucp_113.ui.viewmodel.pendaftaran.PendaftaranPenyediaViewModel
import com.example.finalucp_113.ui.viewmodel.pendaftaran.UpdatePendaftaranViewModel
import com.example.finalucp_113.ui.viewmodel.siswa.HomeSiswaViewModel
import com.example.finalucp_113.ui.viewmodel.siswa.SiswaPenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiUpdatePendaftaran : DestinasiNavigasi {
    override val route = "update_pendaftaran"
    override val titleRes = "Update Pendaftaran"
    const val id_pendaftaran = "id_pendaftaran"
    val routeWithArgs = "$route/{$id_pendaftaran}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePendaftaranView(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePendaftaranViewModel = viewModel(factory = PendaftaranPenyediaViewModel.Factory),
    viewModelSiswa: HomeSiswaViewModel = viewModel(factory = SiswaPenyediaViewModel.Factory),
    viewModelKursus : HomeKursusViewModel = viewModel(factory = KursusPenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdatePendaftaran.titleRes,
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        InsertBodyPendaftaran(
            insertPendaftaranUIState = viewModel.uiState,
            onValueChange = viewModel::updateInsertPendaftaranState,
            onClick = {
                coroutineScope.launch {
                    viewModel.UpdatePendaftaran()
                    navigateBack()
                }
            },
            modifier = modifier
                .padding(innerPadding)
                .offset(y = (-70).dp)
        )
    }
}

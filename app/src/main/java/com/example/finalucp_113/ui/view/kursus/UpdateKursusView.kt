package com.example.finalucp_113.ui.view.kursus

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalucp_113.ui.customwidget.CostumeTopAppBar
import com.example.finalucp_113.ui.navigation.DestinasiNavigasi
import com.example.finalucp_113.ui.viewmodel.instruktur.HomeInstrukturViewModel
import com.example.finalucp_113.ui.viewmodel.instruktur.InstrukturPenyediaViewModel
import com.example.finalucp_113.ui.viewmodel.kursus.KursusPenyediaViewModel
import com.example.finalucp_113.ui.viewmodel.kursus.UpdateKursusViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateKursus : DestinasiNavigasi {
    override val route = "update_kursus"
    override val titleRes = "Update Kursus"
    const val id_kursus = "id_kursus"
    val routeWithArgs = "$route/{$id_kursus}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateKursusView(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateKursusViewModel = viewModel(factory = KursusPenyediaViewModel.Factory),
    viewModelInstruktur : HomeInstrukturViewModel = viewModel(factory = InstrukturPenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateKursus.titleRes,
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF46051C))
                .padding(innerPadding)
        ) {
            InsertBodyKursus(
                insertKursusUIState = viewModel.uiState,
                onValueChange = viewModel::updateInsertKursusState,
                onClick = {
                    coroutineScope.launch {
                        viewModel.UpdateKursus()
                        navigateBack()
                    }
                },
                modifier = modifier
            )
        }
    }
}

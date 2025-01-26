package com.example.finalucp_113.ui.view.instruktur

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalucp_113.ui.customwidget.CostumeTopAppBar
import com.example.finalucp_113.ui.navigation.DestinasiNavigasi
import com.example.finalucp_113.ui.viewmodel.instruktur.InstrukturPenyediaViewModel
import com.example.finalucp_113.ui.viewmodel.instruktur.UpdateInstrukturViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateInstruktur : DestinasiNavigasi {
    override val route = "id_instruktur"
    override val titleRes = "Update Instruktur"
    const val id_instruktur = "id_instruktur"
    val routeWithArgs = "$route/{$id_instruktur}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateInstrukturView(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateInstrukturViewModel = viewModel(factory = InstrukturPenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateInstruktur.titleRes,
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        InsertBodyInstruktur(
            insertInstrukturUIState = viewModel.uiState,
            onValueChange = viewModel::updateInstrukturState,
            onClick = {
                coroutineScope.launch {
                    viewModel.UpdateInsertInstruktur()
                    navigateBack()
                }
            },
            modifier = modifier
                .padding(innerPadding)
                .offset(y = (-70).dp)
        )
    }
}

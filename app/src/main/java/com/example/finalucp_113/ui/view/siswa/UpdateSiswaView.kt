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
import com.example.finalucp_113.ui.view.siswa.InsertBodySiswa
import com.example.finalucp_113.ui.viewmodel.siswa.SiswaPenyediaViewModel
import com.example.finalucp_113.ui.viewmodel.siswa.UpdateSiswaViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateSiswa : DestinasiNavigasi {
    override val route = "update_siswa"
    override val titleRes = "Update Siswa"
    const val id_siswa = "id_siswa"
    val routeWithArgs = "$route/{$id_siswa}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateSiswaView(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateSiswaViewModel = viewModel(factory = SiswaPenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateSiswa.titleRes,
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
            InsertBodySiswa(
                insertSiswaUIState = viewModel.uiState,
                onValueChange = viewModel::updateInsertSiswaState,
                onClick = {
                    coroutineScope.launch {
                        viewModel.UpdateSiswa()
                        navigateBack()
                    }
                },
                modifier = modifier
            )
        }
    }
}

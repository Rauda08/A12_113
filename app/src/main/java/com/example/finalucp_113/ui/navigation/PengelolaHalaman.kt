package com.example.finalucp_113.ui.navigation

import DestinasiUpdateSiswa
import UpdateSiswaView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.finalucp_113.ui.view.DestinasiHome
import com.example.finalucp_113.ui.view.HomeMenuView
import com.example.finalucp_113.ui.view.siswa.DestinasiDetailSiswa
import com.example.finalucp_113.ui.view.siswa.DestinasiHomeSiswa
import com.example.finalucp_113.ui.view.siswa.DestinasiInsertSiswa
import com.example.finalucp_113.ui.view.siswa.DetailSiswaView
import com.example.finalucp_113.ui.view.siswa.HomeSiswaView
import com.example.finalucp_113.ui.view.siswa.InsertSiswaView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier
    ) {
        composable(
            route = DestinasiHome.route
        ) {
            HomeMenuView(
                onSiswaClick = { navController.navigate(DestinasiHomeSiswa.route) },
            )
        }
        composable(
            route = DestinasiHomeSiswa.route
        ) {
            HomeSiswaView(
                onAddSiswa = {
                    navController.navigate(DestinasiInsertSiswa.route)
                },
                onDetailClick = { id_siswa ->
                    navController.navigate("${DestinasiDetailSiswa.route}/$id_siswa")
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiInsertSiswa.route
        ) {
            InsertSiswaView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = Modifier
            )
        }

        composable(
            route = DestinasiDetailSiswa.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailSiswa.id_siswa) {
                    type = NavType.StringType
                }
            )
        ) {
            val id_siswa = it.arguments?.getString(DestinasiDetailSiswa.id_siswa)

            id_siswa?.let { id_siswa ->
                DetailSiswaView(
                    navigateBack = {
                        navController.popBackStack()
                    },
                    onEditClick = { id_siswa ->
                        navController.navigate("${DestinasiUpdateSiswa.route}/$id_siswa")
                    }
                )
            }
        }

        composable(
            DestinasiUpdateSiswa.routeWithArgs,
            arguments = listOf(
                navArgument (DestinasiUpdateSiswa.id_siswa) {
                    type = NavType.StringType
                }
            )
        ) {
            UpdateSiswaView(
                navigateBack = {
                    navController.popBackStack()
                },
                onNavigateUp = {
                    navController.navigate(
                        DestinasiUpdateSiswa.route
                    ){
                        popUpTo(DestinasiHome.route){
                            inclusive = true
                        }
                    }
                },
            )
        }
    }
}
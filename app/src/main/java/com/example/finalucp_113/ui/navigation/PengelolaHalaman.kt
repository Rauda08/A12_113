package com.example.finalucp_113.ui.navigation

import UpdateSiswaView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.finalucp_113.ui.view.*
import com.example.finalucp_113.ui.view.instruktur.*
import com.example.finalucp_113.ui.view.kursus.*
import com.example.finalucp_113.ui.view.pendaftaran.*
import com.example.finalucp_113.ui.view.siswa.*

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
        /////////////Halaman Home////////////////

        composable(route = DestinasiHome.route) {
            HomeMenuView(
                onSiswaClick = { navController.navigate(DestinasiHomeSiswa.route) },
                onInstrukturClick = { navController.navigate(DestinasiHomeInstruktur.route) },
                onKursusClick = { navController.navigate(DestinasiHomeKursus.route) },
                onPendaftaranClick = { navController.navigate(DestinasiHomePendaftaran.route) }
            )
        }

        /////////////Navigasi Siswa/////////////

        composable(route = DestinasiHomeSiswa.route) {
            HomeSiswaView(
                onAddSiswa = { navController.navigate(DestinasiInsertSiswa.route) },
                onDetailClick = { id_siswa ->
                    navController.navigate("${DestinasiDetailSiswa.route}/$id_siswa")
                },
                modifier = modifier
            )
        }
        composable(route = DestinasiInsertSiswa.route) {
            InsertSiswaView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() }
            )
        }
        composable(
            route = DestinasiDetailSiswa.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailSiswa.id_siswa) { type = NavType.StringType }
            )
        ) {
            val id_siswa = it.arguments?.getString(DestinasiDetailSiswa.id_siswa)
            id_siswa?.let { id ->
                DetailSiswaView(
                    navigateBack = { navController.popBackStack() },
                    onEditClick = { navController.navigate("${DestinasiUpdateSiswa.route}/$id") }
                )
            }
        }
        composable(
            route = DestinasiUpdateSiswa.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiUpdateSiswa.id_siswa) { type = NavType.StringType }
            )
        ) {
            UpdateSiswaView(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) { inclusive = true }
                    }
                }
            )
        }


        ///////// Navigasi Instruktur//////////////


        composable(route = DestinasiHomeInstruktur.route) {
            HomeInstrukturView(
                onAddInstruktur = { navController.navigate(DestinasiInsertInstruktur.route) },
                onDetailClick = { id_instruktur ->
                    navController.navigate("${DestinasiDetailInstruktur.route}/$id_instruktur")
                },
                modifier = modifier
            )
        }
        composable(route = DestinasiInsertInstruktur.route) {
            InsertInstrukturView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() }
            )
        }
        composable(
            route = DestinasiDetailInstruktur.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailInstruktur.id_instruktur) { type = NavType.StringType }
            )
        ) {
            val id_instruktur = it.arguments?.getString(DestinasiDetailInstruktur.id_instruktur)
            id_instruktur?.let { id ->
                DetailInstrukturView(
                    navigateBack = { navController.popBackStack() },
                    onEditClick = { navController.navigate("${DestinasiUpdateInstruktur.route}/$id") }
                )
            }
        }
        composable(
            route = DestinasiUpdateInstruktur.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiUpdateInstruktur.id_instruktur) { type = NavType.StringType }
            )
        ) {
            UpdateInstrukturView(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) { inclusive = true }
                    }
                }
            )
        }

        /////////// Navigasi Kursus////////////

        composable(route = DestinasiHomeKursus.route) {
            HomeKursusView(
                onAddKursus = { navController.navigate(DestinasiInsertKursus.route) },
                onDetailClick = { id_kursus ->
                    navController.navigate("${DestinasiDetailKursus.route}/$id_kursus")
                },
                modifier = modifier
            )
        }
        composable(route = DestinasiInsertKursus.route) {
            InsertKursusView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() }
            )
        }
        composable(
            route = DestinasiDetailKursus.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailKursus.id_kursus) { type = NavType.StringType }
            )
        ) {
            val id_kursus = it.arguments?.getString(DestinasiDetailKursus.id_kursus)
            id_kursus?.let { id ->
                DetailkursusView(
                    navigateBack = { navController.popBackStack() },
                    onEditClick = { navController.navigate("${DestinasiUpdateKursus.route}/$id") }
                )
            }
        }
        composable(
            route = DestinasiUpdateKursus.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiUpdateKursus.id_kursus) { type = NavType.StringType }
            )
        ) {
            UpdateKursusView(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) { inclusive = true }
                    }
                }
            )
        }

        ////////// Navigasi Pendaftaran//////////////

        composable(route = DestinasiHomePendaftaran.route) {
            HomePendaftaranView(
                onAddPendaftaran = { navController.navigate(DestinasiInsertPendaftaran.route) },
                onDetailClick = { id_pendaftaran ->
                    navController.navigate("${DestinasiDetailPendaftaran.route}/$id_pendaftaran")
                },
                modifier = modifier
            )
        }
        composable(route = DestinasiInsertPendaftaran.route) {
            InsertPendaftaranView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() }
            )
        }
        composable(
            route = DestinasiDetailPendaftaran.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailPendaftaran.id_pendaftaran) { type = NavType.StringType }
            )
        ) {
            val id_pendaftaran = it.arguments?.getString(DestinasiDetailPendaftaran.id_pendaftaran)
            id_pendaftaran?.let { id ->
                DetailPendaftaranView(
                    navigateBack = { navController.popBackStack() },
                    onEditClick = { navController.navigate("${DestinasiUpdatePendaftaran.route}/$id") }
                )
            }
        }
        composable(
            route = DestinasiUpdatePendaftaran.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiUpdatePendaftaran.id_pendaftaran) { type = NavType.StringType }
            )
        ) {
            UpdatePendaftaranView(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) { inclusive = true }
                    }
                }
            )
        }
    }
}

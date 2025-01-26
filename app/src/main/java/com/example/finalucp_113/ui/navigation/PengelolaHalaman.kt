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
import com.example.finalucp_113.ui.view.instruktur.DestinasiDetailInstruktur
import com.example.finalucp_113.ui.view.instruktur.DestinasiHomeInstruktur
import com.example.finalucp_113.ui.view.instruktur.DestinasiInsertInstruktur
import com.example.finalucp_113.ui.view.instruktur.DestinasiUpdateInstruktur
import com.example.finalucp_113.ui.view.instruktur.DetailInstrukturView
import com.example.finalucp_113.ui.view.instruktur.HomeInstrukturView
import com.example.finalucp_113.ui.view.instruktur.InsertInstrukturView
import com.example.finalucp_113.ui.view.instruktur.UpdateInstrukturView
import com.example.finalucp_113.ui.view.kursus.DestinasiDetailKursus
import com.example.finalucp_113.ui.view.kursus.DestinasiHomeKursus
import com.example.finalucp_113.ui.view.kursus.DestinasiInsertKursus
import com.example.finalucp_113.ui.view.kursus.DestinasiUpdateKursus
import com.example.finalucp_113.ui.view.kursus.DetailkursusView
import com.example.finalucp_113.ui.view.kursus.HomeKursusView
import com.example.finalucp_113.ui.view.kursus.InsertKursusView
import com.example.finalucp_113.ui.view.kursus.UpdateKursusView
import com.example.finalucp_113.ui.view.pendaftaran.DestinasiDetailPendaftaran
import com.example.finalucp_113.ui.view.pendaftaran.DestinasiHomePendaftaran
import com.example.finalucp_113.ui.view.pendaftaran.DestinasiInsertPendaftaran
import com.example.finalucp_113.ui.view.pendaftaran.DestinasiUpdatePendaftaran
import com.example.finalucp_113.ui.view.pendaftaran.DetailPendaftaranView
import com.example.finalucp_113.ui.view.pendaftaran.HomePendaftaranView
import com.example.finalucp_113.ui.view.pendaftaran.InsertPendaftaranView
import com.example.finalucp_113.ui.view.pendaftaran.UpdatePendaftaranView
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
                onInstrukturClick = { navController.navigate(DestinasiHomeInstruktur.route)},
                onKursusClick = { navController.navigate(DestinasiHomeKursus.route)},
                onPendaftaranClick = { navController.navigate(DestinasiHomePendaftaran.route)}
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

        //Instruktur

        composable(
            route = DestinasiHomeInstruktur.route
        ) {
            HomeInstrukturView(
                onAddInstruktur = {
                    navController.navigate(DestinasiInsertInstruktur.route)
                },
                onDetailClick = { id_instruktur ->
                    navController.navigate("${DestinasiDetailInstruktur.route}/$id_instruktur")
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiInsertInstruktur.route
        ) {
            InsertInstrukturView(
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
            route = DestinasiDetailInstruktur.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailInstruktur.id_instruktur) {
                    type = NavType.StringType
                }
            )
        ) {
            val id_instruktur = it.arguments?.getString(DestinasiDetailInstruktur.id_instruktur)

            id_instruktur?.let { id_instruktur ->
                DetailInstrukturView(
                    navigateBack = {
                        navController.popBackStack()
                    },
                    onEditClick = { id_instruktur ->
                        navController.navigate("${DestinasiUpdateInstruktur.route}/$id_instruktur")
                    }
                )
            }
        }

        composable(
            DestinasiUpdateInstruktur.routeWithArgs,
            arguments = listOf(
                navArgument (DestinasiUpdateInstruktur.id_instruktur) {
                    type = NavType.StringType
                }
            )
        ) {
            UpdateInstrukturView(
                navigateBack = {
                    navController.popBackStack()
                },
                onNavigateUp = {
                    navController.navigate(
                        DestinasiUpdateInstruktur.route
                    ){
                        popUpTo(DestinasiHome.route){
                            inclusive = true
                        }
                    }
                },
            )
        }

        // Kursus

        composable(
            route = DestinasiHomeKursus.route
        ) {
            HomeKursusView(
                onAddKursus = {
                    navController.navigate(DestinasiInsertKursus.route)
                },
                onDetailClick = { id_kursus ->
                    navController.navigate("${DestinasiDetailKursus.route}/$id_kursus")
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiInsertKursus.route
        ) {
            InsertKursusView(
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
            route = DestinasiDetailKursus.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailKursus.id_kursus) {
                    type = NavType.StringType
                }
            )
        ) {
            val id_kursus = it.arguments?.getString(DestinasiDetailKursus.id_kursus)

            id_kursus?.let { id_kursus ->
                DetailkursusView(
                    navigateBack = {
                        navController.popBackStack()
                    },
                    onEditClick = { id_kursus ->
                        navController.navigate("${DestinasiUpdateKursus.route}/$id_kursus")
                    }
                )
            }
        }

        composable(
            DestinasiUpdateKursus.routeWithArgs,
            arguments = listOf(
                navArgument (DestinasiUpdateKursus.id_kursus) {
                    type = NavType.StringType
                }
            )
        ) {
            UpdateKursusView(
                navigateBack = {
                    navController.popBackStack()
                },
                onNavigateUp = {
                    navController.navigate(
                        DestinasiUpdateKursus.route
                    ){
                        popUpTo(DestinasiHome.route){
                            inclusive = true
                        }
                    }
                },
            )
        }

        ///////Pendaftaran/////////////

        composable(
            route = DestinasiHomePendaftaran.route
        ) {
            HomePendaftaranView(
                onAddPendaftaran = {
                    navController.navigate(DestinasiInsertPendaftaran.route)
                },
                onDetailClick = { id_pendaftaran ->
                    navController.navigate("${DestinasiDetailPendaftaran.route}/$id_pendaftaran")
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiInsertPendaftaran.route
        ) {
            InsertPendaftaranView(
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
            route = DestinasiDetailPendaftaran.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailPendaftaran.id_pendaftaran) {
                    type = NavType.StringType
                }
            )
        ) {
            val id_pendaftaran = it.arguments?.getString(DestinasiDetailPendaftaran.id_pendaftaran)

            id_pendaftaran?.let { id_pendaftaran ->
                DetailPendaftaranView(
                    navigateBack = {
                        navController.popBackStack()
                    },
                    onEditClick = { id_pendaftaran ->
                        navController.navigate("${DestinasiUpdatePendaftaran.route}/$id_pendaftaran")
                    }
                )
            }
        }
        composable(
            DestinasiUpdateKursus.routeWithArgs,
            arguments = listOf(
                navArgument (DestinasiUpdateKursus.id_kursus) {
                    type = NavType.StringType
                }
            )
        ) {
            UpdatePendaftaranView(
                navigateBack = {
                    navController.popBackStack()
                },
                onNavigateUp = {
                    navController.navigate(
                        DestinasiUpdatePendaftaran.route
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
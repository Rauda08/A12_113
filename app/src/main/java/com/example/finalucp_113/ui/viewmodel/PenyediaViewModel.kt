package com.example.finalucp_113.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalucp_113.LembagaKursusAplications
import com.example.finalucp_113.ui.viewmodel.instruktur.DetailInstrukturViewModel
import com.example.finalucp_113.ui.viewmodel.instruktur.HomeInstrukturViewModel
import com.example.finalucp_113.ui.viewmodel.instruktur.InsertInstrukturViewmodel
import com.example.finalucp_113.ui.viewmodel.instruktur.UpdateInstrukturViewModel
import com.example.finalucp_113.ui.viewmodel.siswa.DetailsiswaViewModel
import com.example.finalucp_113.ui.viewmodel.siswa.HomeSiswaViewModel
import com.example.finalucp_113.ui.viewmodel.siswa.InsertSiswaViewModel
import com.example.finalucp_113.ui.viewmodel.siswa.UpdateSiswaViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeSiswaViewModel(lembagakursusApp().container.siswaRepository) }
        initializer { InsertSiswaViewModel(lembagakursusApp().container.siswaRepository)}
        initializer {
            DetailsiswaViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                siswaRepository = lembagakursusApp().container.siswaRepository
            )}
        initializer {
            UpdateSiswaViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                siswaRepository = lembagakursusApp().container.siswaRepository
            )}

        ////////////////////////////////////////////////////////////////////////////////////

        initializer { HomeInstrukturViewModel(lembagakursusApp().container.instrukturRepository) }
        initializer { InsertInstrukturViewmodel(lembagakursusApp().container.instrukturRepository)}
        initializer {
            DetailInstrukturViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                instrukturRepository = lembagakursusApp().container.instrukturRepository
            )}
        initializer {
            UpdateInstrukturViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                instrukturRepository = lembagakursusApp().container.instrukturRepository
            )}
    }
}

fun CreationExtras.lembagakursusApp(): LembagaKursusAplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LembagaKursusAplications)

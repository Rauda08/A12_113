package com.example.finalucp_113.ui.viewmodel.siswa

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalucp_113.LembagaKursusAplications

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
    }
}

fun CreationExtras.lembagakursusApp(): LembagaKursusAplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LembagaKursusAplications)

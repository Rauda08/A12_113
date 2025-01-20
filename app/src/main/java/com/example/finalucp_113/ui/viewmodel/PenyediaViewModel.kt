package com.example.finalucp_113.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalucp_113.LembagaKursusAplications
import com.example.finalucp_113.ui.viewmodel.siswa.HomeViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeViewModel(lembagakursusApp().container.siswaRepository) }
    }
}

fun CreationExtras.lembagakursusApp(): LembagaKursusAplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LembagaKursusAplications)

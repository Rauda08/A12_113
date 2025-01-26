package com.example.finalucp_113.ui.viewmodel.pendaftaran

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalucp_113.LembagaKursusAplications

object PendaftaranPenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomePendaftaranViewModel(lembagakursusApp().container.pendaftaranRepository) }
        initializer { InsertPendaftaranViewModel(lembagakursusApp().container.pendaftaranRepository) }
        initializer {
            DetailpendaftaranViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                pendaftaranRepository = lembagakursusApp().container.pendaftaranRepository
            )
        }
        initializer {
            UpdatePendaftaranViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                pendaftaranRepository = lembagakursusApp().container.pendaftaranRepository
            )
        }
    }
}

fun CreationExtras.lembagakursusApp(): LembagaKursusAplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LembagaKursusAplications)

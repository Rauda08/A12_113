package com.example.finalucp_113.ui.viewmodel.instruktur

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalucp_113.LembagaKursusAplications

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeInstrukturViewModel(lembagakursusApp().container.instrukturRepository) }
        initializer { InsertInstrukturViewmodel(lembagakursusApp().container.instrukturRepository) }
        initializer {
            DetailInstrukturViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                instrukturRepository = lembagakursusApp().container.instrukturRepository
            )
        }
        initializer {
            UpdateInstrukturViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                instrukturRepository = lembagakursusApp().container.instrukturRepository
            )
        }
    }
}

fun CreationExtras.lembagakursusApp(): LembagaKursusAplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LembagaKursusAplications)

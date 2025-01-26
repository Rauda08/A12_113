package com.example.finalucp_113.ui.viewmodel.kursus

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalucp_113.LembagaKursusAplications

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer {
            HomeKursusViewModel(
                lembagakursusApp().container.kursusRepository
            )
        }
        initializer{
            InsertKursusViewModel(
                lembagakursusApp().container.kursusRepository
            )
        }

        initializer{
            DetailkursusViewModel(
                createSavedStateHandle(),
                lembagakursusApp().container.kursusRepository
            )
        }

        initializer{
            UpdateKursusViewModel(
                createSavedStateHandle(),
                lembagakursusApp().container.kursusRepository
            )
        }
    }
}
fun CreationExtras.lembagakursusApp(): LembagaKursusAplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LembagaKursusAplications)

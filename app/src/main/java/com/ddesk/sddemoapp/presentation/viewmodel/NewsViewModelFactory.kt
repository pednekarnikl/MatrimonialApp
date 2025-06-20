package com.ddesk.sddemoapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ddesk.sddemoapp.domain.usecase.DeleteSavedMatchesUseCase
import com.ddesk.sddemoapp.domain.usecase.GetNewMatchesUseCase
import com.ddesk.sddemoapp.domain.usecase.GetSavedMatchesUseCase
import com.ddesk.sddemoapp.domain.usecase.SaveMatchesUseCase

class MatchesViewModelFactory(
    private val app:Application,
    private val getNewMatchesUseCase: GetNewMatchesUseCase,
    private val saveMatchesUseCase: SaveMatchesUseCase,
    private val getSavedMatchesUseCase: GetSavedMatchesUseCase,
    private val deleteSavedMatchesUseCase: DeleteSavedMatchesUseCase
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MatchesViewModel(
            app,
            getNewMatchesUseCase,
            saveMatchesUseCase,
            getSavedMatchesUseCase,
            deleteSavedMatchesUseCase
        ) as T
    }
}










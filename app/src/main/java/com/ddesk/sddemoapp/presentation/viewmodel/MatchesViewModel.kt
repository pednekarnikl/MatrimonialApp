package com.ddesk.sddemoapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ddesk.sddemoapp.data.model.UserEntity
import com.ddesk.sddemoapp.data.util.Resource
import com.ddesk.sddemoapp.domain.usecase.DeleteSavedMatchesUseCase
import com.ddesk.sddemoapp.domain.usecase.GetNewMatchesUseCase
import com.ddesk.sddemoapp.domain.usecase.GetSavedMatchesUseCase
import com.ddesk.sddemoapp.domain.usecase.SaveMatchesUseCase
import com.ddesk.sddemoapp.presentation.utils.isNetworkAvailable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Collections

class MatchesViewModel(
    private val app: Application,
    private val getNewMatchesUseCase: GetNewMatchesUseCase,
    private val saveMatchesUseCase: SaveMatchesUseCase,
    private val getSavedMatchesUseCase: GetSavedMatchesUseCase,
    private val deleteSavedMatchesUseCase: DeleteSavedMatchesUseCase
) : AndroidViewModel(application = app) {

    private val _newMatches = MutableStateFlow<Resource<List<UserEntity>>>(Resource.Loading())
    val newMatches: StateFlow<Resource<List<UserEntity>>> = _newMatches

    private val _favoriteMatches = MutableStateFlow<List<UserEntity>>(Collections.emptyList())
    val favoriteMatches: StateFlow<List<UserEntity>> = _favoriteMatches

    // For retry tracking
    private var retryCount = 0
    private val maxRetries = 3

    fun saveUserToDb(user: UserEntity) = viewModelScope.launch(Dispatchers.IO) {
        saveMatchesUseCase.execute(user)
        removeUserFromList(user)
    }

    fun removeUserFromList(user: UserEntity) = viewModelScope.launch(Dispatchers.IO) {
        _newMatches.update { currentResource ->
            when (currentResource) {
                is Resource.Success -> {
                    val currentList = currentResource.data!!.toMutableList()
                    currentList.removeAll { it.id == user.id }
                    Resource.Success(currentList)
                }
                is Resource.Loading -> currentResource
                is Resource.Error -> currentResource
            }
        }
    }

    fun getNewMatches() = viewModelScope.launch(Dispatchers.IO) {
        _newMatches.value = Resource.Loading()
        try {
            if (isNetworkAvailable(app)) {
                // Simulate flaky network - 30% chance of failure
                if (shouldFailRequest()) {
                    throw IOException("Simulated network failure")
                }

                val data = getNewMatchesUseCase.execute()
                retryCount = 0 // Reset retry count on success
                _newMatches.value = data
            } else {
                _newMatches.value = Resource.Error("No Internet Available")
            }
        } catch (e: Exception) {
            handleRetry(e)
        }
    }

    private fun shouldFailRequest(): Boolean {
        // 30% chance of failure
        return (1..100).random() <= 30
    }

    private suspend fun handleRetry(e: Exception) {
        if (retryCount < maxRetries) {
            retryCount++
            val delayTime = calculateExponentialBackoff(retryCount)
            delay(delayTime)
            getNewMatches() // Retry
        } else {
            retryCount = 0 // Reset for next attempt
            _newMatches.value = Resource.Error(
                message = e.message ?: "Unknown error occurred",
                data = getPartialDataIfAvailable()
            )
        }
    }

    private fun calculateExponentialBackoff(retryCount: Int): Long {
        return minOf(
            1000L * (1L shl retryCount), // Exponential backoff: 2s, 4s, 8s, etc.
            8000L // Max 8 seconds
        )
    }

    private fun getPartialDataIfAvailable(): List<UserEntity>? {
        return (_newMatches.value as? Resource.Success)?.data
    }

    fun getFavoriteMatches() = viewModelScope.launch(Dispatchers.IO) {
        getSavedMatchesUseCase.execute().collect {
            _favoriteMatches.value = it
        }
    }

    fun deleteFavoriteMatch(user: UserEntity) = viewModelScope.launch(Dispatchers.IO) {
        deleteSavedMatchesUseCase.execute(user)
    }
}
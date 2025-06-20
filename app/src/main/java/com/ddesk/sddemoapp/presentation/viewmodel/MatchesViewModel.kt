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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
                val data = getNewMatchesUseCase.execute()
                _newMatches.value = data
            } else _newMatches.value = Resource.Error("No Internet Available")
        } catch (e: Exception) {
            _newMatches.value = Resource.Error(e.message.toString())
        }

    }

    fun getFavoriteMatches() = viewModelScope.launch(Dispatchers.IO) {
        getSavedMatchesUseCase.execute().collect{
            _favoriteMatches.value = it
        }
    }

    fun deleteFavoriteMatch(user: UserEntity) = viewModelScope.launch(Dispatchers.IO) {
        deleteSavedMatchesUseCase.execute(user)
    }
}
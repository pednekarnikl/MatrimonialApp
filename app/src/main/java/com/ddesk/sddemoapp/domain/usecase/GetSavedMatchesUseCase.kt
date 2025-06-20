package com.ddesk.sddemoapp.domain.usecase

import com.ddesk.sddemoapp.data.model.UserEntity
import com.ddesk.sddemoapp.domain.repository.MatchesRepository
import kotlinx.coroutines.flow.Flow

class GetSavedMatchesUseCase(private val matchesRepository: MatchesRepository) {
    fun execute(): Flow<List<UserEntity>> = matchesRepository.getMatchLocalDB()
}
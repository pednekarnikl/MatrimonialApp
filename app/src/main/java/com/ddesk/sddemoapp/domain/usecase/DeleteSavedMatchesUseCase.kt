package com.ddesk.sddemoapp.domain.usecase

import com.ddesk.sddemoapp.data.model.UserEntity
import com.ddesk.sddemoapp.domain.repository.MatchesRepository

class DeleteSavedMatchesUseCase(private val matchesRepository: MatchesRepository) {
    suspend fun execute(userEntity: UserEntity) = matchesRepository.deleteMatchLocalDB(userEntity)
}
package com.ddesk.sddemoapp.domain.usecase

import com.ddesk.sddemoapp.data.model.UserEntity
import com.ddesk.sddemoapp.data.util.Resource
import com.ddesk.sddemoapp.data.util.UserMapper
import com.ddesk.sddemoapp.domain.repository.MatchesRepository
import com.ddesk.sddemoapp.presentation.utils.calculateCompatibility
import kotlin.math.abs

class GetNewMatchesUseCase(private val matchesRepository: MatchesRepository) {

    private val userMapper = UserMapper()

    private val currentUserAge = 30
    private val currentUserCity = "New York"

    suspend fun execute():Resource<List<UserEntity>>{
        return when (val response = matchesRepository.getNewMatches()) {
            is Resource.Success -> {
                val matches = response.data?.results ?: emptyList()
                val sortedEntities = userMapper.mapToEntityList(matches)
                    .sortedByDescending { user ->
                        calculateCompatibility(
                            currentUserAge = currentUserAge,
                            currentUserCity = currentUserCity,
                            matchAge = user.age?.toInt()?:30,
                            matchCity = user.city?:""
                        )
                    }
                Resource.Success(sortedEntities)
            }
            is Resource.Loading -> Resource.Loading()
            is Resource.Error -> Resource.Error(response.message ?: "No Data Found!", emptyList())
        }
    }

}

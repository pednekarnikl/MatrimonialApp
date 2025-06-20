package com.ddesk.sddemoapp.data.util

import com.ddesk.sddemoapp.data.model.NewMatchesApiResponse
import com.ddesk.sddemoapp.data.model.UserEntity
import com.ddesk.sddemoapp.presentation.utils.calculateCompatibility

class UserMapper : Mapper<UserEntity, NewMatchesApiResponse.Result> {

    override fun mapFromEntity(entity: UserEntity): NewMatchesApiResponse.Result? {
        return null
    }

    override fun mapToEntity(result: NewMatchesApiResponse.Result): UserEntity? {
        if (result.id.value?.isBlank() == true) return null

        return UserEntity(
            cell = result.cell,
            age = result.dob?.age.toString(),
            email = result.email,
            gender = result.gender,
            id = result.id.value,
            fullName = "${result.name?.title} ${result.name?.first} ${result.name?.last}",
            phone = result.phone,
            picture = result.picture?.large,
            street = result.location?.street?.name,
            city = result.location?.city,
            state = result.location?.state,
            religion = "Hindu",
            education = "MBBS",
            matchScore = calculateCompatibility(
                currentUserAge = 30,
                currentUserCity = "New York",
                matchAge = result.dob?.age ?: 30,
                matchCity = result.location?.city ?: ""
            )
        )
    }

    fun mapToEntityList(results: List<NewMatchesApiResponse.Result>): List<UserEntity> {
        return results.mapNotNull { mapToEntity(it) }
    }
}
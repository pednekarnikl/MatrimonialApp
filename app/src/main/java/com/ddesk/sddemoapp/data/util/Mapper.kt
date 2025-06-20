package com.ddesk.sddemoapp.data.util

interface Mapper<E, R> {
    fun mapFromEntity(entity: E): R?
    fun mapToEntity(domain: R): E?
}
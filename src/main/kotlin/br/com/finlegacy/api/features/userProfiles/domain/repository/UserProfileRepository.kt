package br.com.finlegacy.api.features.userProfiles.domain.repository

import br.com.finlegacy.api.features.userProfiles.controller.dto.request.UserProfileCreateRequest
import br.com.finlegacy.api.features.userProfiles.domain.model.UserProfile
import br.com.finlegacy.api.features.userProfiles.controller.dto.request.UserProfileUpdateRequest

interface UserProfileRepository {
    suspend fun findById(id: Long): UserProfile?
    suspend fun findAll(): List<UserProfile>
    suspend fun delete(id: Long): Boolean
    suspend fun update(userProfileUpdateRequest: UserProfileUpdateRequest): UserProfile?
    suspend fun create(userProfileCreateRequest: UserProfileCreateRequest): UserProfile
}


package br.com.finlegacy.api.features.userProfiles.domain.repository

import br.com.finlegacy.api.features.userProfiles.domain.model.UserProfileCreate
import br.com.finlegacy.api.features.userProfiles.domain.model.UserProfileInfo
import br.com.finlegacy.api.features.userProfiles.domain.model.UserProfileUpdate

interface UserProfileRepository {
    suspend fun findById(id: Long): UserProfileInfo?
    suspend fun findAll(): List<UserProfileInfo>
    suspend fun delete(id: Long): Boolean
    suspend fun update(userProfileUpdate: UserProfileUpdate): UserProfileInfo?
    suspend fun create(userProfileCreate: UserProfileCreate): UserProfileInfo
}


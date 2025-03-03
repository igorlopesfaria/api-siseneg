package br.com.finlegacy.api.features.userProfile.domain.respository

import br.com.finlegacy.api.features.userProfile.domain.model.UserProfileCreate
import br.com.finlegacy.api.features.userProfile.domain.model.UserProfileInfo
import br.com.finlegacy.api.features.userProfile.domain.model.UserProfileUpdate

interface UserProfileRepository {
    suspend fun findById(id: Long): UserProfileInfo?
    suspend fun findAll(): List<UserProfileInfo>
    suspend fun delete(id: Long): Boolean
    suspend fun update(userProfileUpdate: UserProfileUpdate): UserProfileInfo?
    suspend fun create(userProfileCreate: UserProfileCreate): UserProfileInfo
}


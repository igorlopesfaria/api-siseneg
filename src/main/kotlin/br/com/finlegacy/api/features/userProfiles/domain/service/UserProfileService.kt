package br.com.finlegacy.api.features.userProfiles.domain.service
import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.userProfiles.domain.model.UserProfile
import br.com.finlegacy.api.features.userProfiles.controller.dto.request.UserProfileCreateRequest
import br.com.finlegacy.api.features.userProfiles.controller.dto.request.UserProfileUpdateRequest

interface UserProfileService {
    suspend fun findById(id: Long, uidLogged: String): Result<UserProfile>
    suspend fun findAll(uidLogged: String): Result<List<UserProfile>>
    suspend fun delete(id: Long, uidLogged: String): Result<Boolean>
    suspend fun update(userProfileUpdate: UserProfileUpdateRequest, uidLogged: String): Result<UserProfile>
    suspend fun create(userProfileCreate: UserProfileCreateRequest, uidLogged: String): Result<UserProfile>
}


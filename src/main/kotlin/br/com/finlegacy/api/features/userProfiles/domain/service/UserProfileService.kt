package br.com.finlegacy.api.features.userProfiles.domain.service
import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.userProfiles.domain.model.UserProfileInfo
import br.com.finlegacy.api.features.userProfiles.domain.model.UserProfileCreate
import br.com.finlegacy.api.features.userProfiles.domain.model.UserProfileUpdate

interface UserProfileService {
    suspend fun findById(id: Long, uidLogged: String): Result<UserProfileInfo>
    suspend fun findAll(uidLogged: String): Result<List<UserProfileInfo>>
    suspend fun delete(id: Long, uidLogged: String): Result<Boolean>
    suspend fun update(clinicUpdate: UserProfileUpdate, uidLogged: String): Result<UserProfileInfo>
    suspend fun create(clinicCreate: UserProfileCreate, uidLogged: String): Result<UserProfileInfo>
}


package br.com.finlegacy.api.features.userProfiles.domain.service

import br.com.finlegacy.api.core.exceptions.ForbiddenException
import br.com.finlegacy.api.core.exceptions.ItemNotFoundException
import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.userProfiles.domain.model.UserProfileInfo
import br.com.finlegacy.api.features.userProfiles.domain.model.UserProfileCreate
import br.com.finlegacy.api.features.userProfiles.domain.model.UserProfileUpdate
import br.com.finlegacy.api.features.userProfiles.domain.repository.UserProfileRepository
import br.com.finlegacy.api.features.users.domain.model.User
import br.com.finlegacy.api.features.users.domain.repository.UserRepository

class UserProfileServiceImpl(
    private val userRepository: UserRepository,
    private val userProfileRepository: UserProfileRepository
): UserProfileService {
    override suspend fun findById(id: Long, uidLogged: String): Result<UserProfileInfo> {
        return runCatching {
            userProfileRepository.findById(id) ?: throw ItemNotFoundException("User Profile")
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun findAll(uidLogged: String): Result<List<UserProfileInfo>> {
        return runCatching {
            userProfileRepository.findAll()
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun delete(id: Long, uidLogged: String): Result<Boolean> {
        return runCatching {
            val userLogged:User = userRepository.findByUid(uidLogged) ?: throw ForbiddenException()
            if (!userLogged.userProfile.isSysAdmin) throw ForbiddenException()

            if (userProfileRepository.delete(id)) {
                true
            } else {
                throw ItemNotFoundException("User Profile")
            }
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun update(clinicUpdate: UserProfileUpdate, uidLogged: String): Result<UserProfileInfo> {
        return runCatching {
            val userLogged:User = userRepository.findByUid(uidLogged) ?: throw ForbiddenException()
            if (!userLogged.userProfile.isSysAdmin) throw ForbiddenException()

            userProfileRepository.update(clinicUpdate) ?: throw ItemNotFoundException("User Profile")
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun create(clinicCreate: UserProfileCreate, uidLogged: String): Result<UserProfileInfo> {
        return runCatching {
            val userLogged:User = userRepository.findByUid(uidLogged) ?: throw ForbiddenException()
            if (!userLogged.userProfile.isSysAdmin) throw ForbiddenException()

            userProfileRepository.create(clinicCreate)
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }
}


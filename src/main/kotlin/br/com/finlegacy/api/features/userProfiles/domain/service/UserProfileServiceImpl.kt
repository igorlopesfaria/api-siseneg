package br.com.finlegacy.api.features.userProfiles.domain.service

import br.com.finlegacy.api.core.exceptions.BadRequestException
import br.com.finlegacy.api.core.exceptions.ForbiddenException
import br.com.finlegacy.api.core.exceptions.ItemNotFoundException
import br.com.finlegacy.api.core.extensions.isValidId
import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.userProfiles.domain.model.UserProfile
import br.com.finlegacy.api.features.userProfiles.controller.dto.request.UserProfileCreateRequest
import br.com.finlegacy.api.features.userProfiles.controller.dto.request.UserProfileUpdateRequest
import br.com.finlegacy.api.features.userProfiles.domain.repository.UserProfileRepository
import br.com.finlegacy.api.features.users.domain.model.User
import br.com.finlegacy.api.features.users.domain.repository.UserRepository

class UserProfileServiceImpl(
    private val userRepository: UserRepository,
    private val userProfileRepository: UserProfileRepository
): UserProfileService {
    override suspend fun findById(id: Long, uidLogged: String): Result<UserProfile> {
        return runCatching {
            if (!id.isValidId()) throw BadRequestException("User Profile id")

            userProfileRepository.findById(id) ?: throw ItemNotFoundException("User Profile")
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun findAll(uidLogged: String): Result<List<UserProfile>> {
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

            if (!id.isValidId()) throw BadRequestException("User Profile id")

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

    override suspend fun update(userProfileUpdate: UserProfileUpdateRequest, uidLogged: String): Result<UserProfile> {
        return runCatching {
            val userLogged:User = userRepository.findByUid(uidLogged) ?: throw ForbiddenException()
            if (!userLogged.userProfile.isSysAdmin) throw ForbiddenException()

            if (!userProfileUpdate.id.isValidId()) throw BadRequestException("User Profile id")
            if (userProfileUpdate.name.isEmpty()) throw BadRequestException("User Profile name")

            userProfileRepository.update(userProfileUpdate) ?: throw ItemNotFoundException("User Profile")
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun create(userProfileCreate: UserProfileCreateRequest, uidLogged: String): Result<UserProfile> {
        return runCatching {
            val userLogged:User = userRepository.findByUid(uidLogged) ?: throw ForbiddenException()
            if (!userLogged.userProfile.isSysAdmin) throw ForbiddenException()

            if (userProfileCreate.name.isEmpty()) throw BadRequestException("User Profile name")

            userProfileRepository.create(userProfileCreate)
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }
}


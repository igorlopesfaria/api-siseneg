package br.com.finlegacy.api.features.maritalStatus.domain.service

import br.com.finlegacy.api.core.exceptions.ForbiddenException
import br.com.finlegacy.api.core.exceptions.ItemNotFoundException
import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.maritalStatus.domain.model.MaritalStatusCreate
import br.com.finlegacy.api.features.maritalStatus.domain.model.MaritalStatusInfo
import br.com.finlegacy.api.features.maritalStatus.domain.model.MaritalStatusUpdate
import br.com.finlegacy.api.features.maritalStatus.domain.repository.MaritalStatusRepository
import br.com.finlegacy.api.features.users.domain.repository.UserRepository

class MaritalStatusServiceImpl(
    private val maritalStatusRepository: MaritalStatusRepository,
    private val userRepository: UserRepository
): MaritalStatusService {

    override suspend fun findById(id: Long): Result<MaritalStatusInfo> {
        return runCatching {
            maritalStatusRepository.findById(id) ?: throw ItemNotFoundException("Marital Status")
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun findAll(): Result<List<MaritalStatusInfo>> {
        return runCatching {
            maritalStatusRepository.findAll()
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }


    override suspend fun delete(id: Long, uidLogged: String): Result<Boolean> {
        return runCatching {
            if (userRepository.findByUid(uidLogged)?.userProfile?.isSysAdmin != true)
                throw ForbiddenException()

            if (maritalStatusRepository.delete(id)) {
                true
            } else {
                throw ItemNotFoundException("Marital Status")
            }
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun update(maritalStatusUpdate: MaritalStatusUpdate, uidLogged: String): Result<MaritalStatusInfo> {

        return runCatching {
            if (userRepository.findByUid(uidLogged)?.userProfile?.isSysAdmin != true)
                throw ForbiddenException()

            maritalStatusRepository.update(maritalStatusUpdate) ?: throw ItemNotFoundException("Marital Status")
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun create(maritalStatusCreate: MaritalStatusCreate, uidLogged: String): Result<MaritalStatusInfo> {
        return runCatching {
            if (userRepository.findByUid(uidLogged)?.userProfile?.isSysAdmin != true)
                throw ForbiddenException()

            maritalStatusRepository.create(maritalStatusCreate)
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

}
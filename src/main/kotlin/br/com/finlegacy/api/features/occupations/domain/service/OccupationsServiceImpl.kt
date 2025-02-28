package br.com.finlegacy.api.features.occupations.domain.service

import br.com.finlegacy.api.core.exceptions.ForbiddenException
import br.com.finlegacy.api.core.exceptions.ItemNotFoundException
import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.occupations.domain.model.OccupationCreate
import br.com.finlegacy.api.features.occupations.domain.model.OccupationInfo
import br.com.finlegacy.api.features.occupations.domain.model.OccupationUpdate
import br.com.finlegacy.api.features.occupations.domain.repository.OccupationRepository
import br.com.finlegacy.api.features.users.domain.repository.UserRepository

class OccupationServiceImpl(
    private val maritalStatusRepository: OccupationRepository,
    private val userRepository: UserRepository
): OccupationService {

    override suspend fun findById(id: Long): Result<OccupationInfo> {
        return runCatching {
            maritalStatusRepository.findById(id) ?: throw ItemNotFoundException("Occupation")
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun findAll(): Result<List<OccupationInfo>> {
        return runCatching {
            maritalStatusRepository.findAll()
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }


    override suspend fun delete(id: Long, uidLogged: String): Result<Boolean> {
        return runCatching {
            if (userRepository.findByUid(uidLogged)?.isAdmin != true)
                throw ForbiddenException()

            if (maritalStatusRepository.delete(id)) {
                true
            } else {
                throw ItemNotFoundException("Occupation")
            }
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun update(maritalStatusUpdate: OccupationUpdate, uidLogged: String): Result<OccupationInfo> {

        return runCatching {
            if (userRepository.findByUid(uidLogged)?.isAdmin != true)
                throw ForbiddenException()

            maritalStatusRepository.update(maritalStatusUpdate) ?: throw ItemNotFoundException("Occupation")
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun create(maritalStatusCreate: OccupationCreate, uidLogged: String): Result<OccupationInfo> {
        return runCatching {
            if (userRepository.findByUid(uidLogged)?.isAdmin != true)
                throw ForbiddenException()

            maritalStatusRepository.create(maritalStatusCreate)
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

}
package br.com.finlegacy.api.features.clinics.domain.service

import br.com.finlegacy.api.core.exceptions.ForbiddenException
import br.com.finlegacy.api.core.exceptions.ItemNotFoundException
import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.banks.domain.repository.BankRepository
import br.com.finlegacy.api.features.clinics.domain.model.ClinicCreate
import br.com.finlegacy.api.features.clinics.domain.model.ClinicInfo
import br.com.finlegacy.api.features.clinics.domain.model.ClinicUpdate
import br.com.finlegacy.api.features.clinics.domain.repository.ClinicRepository
import br.com.finlegacy.api.features.users.domain.repository.UserRepository

class ClinicServiceImpl(
    private val clinicRepository: ClinicRepository,
    private val userRepository: UserRepository
) : ClinicService {

    override suspend fun findById(id: Long, uidLogged: String): Result<ClinicInfo> {
        return runCatching {
            if (userRepository.findByUid(uidLogged)?.userProfile?.isSysAdmin != true)
                throw ForbiddenException()

            clinicRepository.findById(id) ?: throw ItemNotFoundException("Clinic")
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun findAll(uidLogged: String): Result<List<ClinicInfo>> {
        return runCatching {
            if (userRepository.findByUid(uidLogged)?.userProfile?.isSysAdmin != true)
                throw ForbiddenException()

            clinicRepository.findAll()
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun delete(id: Long, uidLogged: String): Result<Boolean> {
        return runCatching {
            if (userRepository.findByUid(uidLogged)?.userProfile?.isSysAdmin != true)
                throw ForbiddenException()

            if (clinicRepository.delete(id)) {
                true
            } else {
                throw ItemNotFoundException("Clinic")
            }
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun update(clinicUpdate: ClinicUpdate, uidLogged: String): Result<ClinicInfo> {
        return runCatching {
            if (userRepository.findByUid(uidLogged)?.userProfile?.isSysAdmin != true)
                throw ForbiddenException()

            clinicRepository.update(clinicUpdate) ?: throw ItemNotFoundException("Clinic")
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun create(clinicCreate: ClinicCreate, uidLogged: String): Result<ClinicInfo> {
        return runCatching {
            if (userRepository.findByUid(uidLogged)?.userProfile?.isSysAdmin != true)
                throw ForbiddenException()

            clinicRepository.create(clinicCreate)
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }
}
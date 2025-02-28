package br.com.finlegacy.api.features.procedures.domain.service

import br.com.finlegacy.api.core.exceptions.ForbiddenException
import br.com.finlegacy.api.core.exceptions.ItemNotFoundException
import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.procedures.domain.model.ProcedureCreate
import br.com.finlegacy.api.features.procedures.domain.model.ProcedureInfo
import br.com.finlegacy.api.features.procedures.domain.model.ProcedureUpdate
import br.com.finlegacy.api.features.procedures.domain.repository.ProcedureRepository
import br.com.finlegacy.api.features.proceduresClinics.domain.repository.ProcedureClinicRepository
import br.com.finlegacy.api.features.users.domain.repository.UserRepository

class ProcedureServiceImpl(
    private val procedureRepository: ProcedureRepository,
    private val userRepository: UserRepository) : ProcedureService {

    override suspend fun findById(id: Long, uidLogged: String): Result<ProcedureInfo> {
        return runCatching {
            if (userRepository.findByUid(uidLogged)?.isAdmin != true) throw ForbiddenException()

            procedureRepository.findById(id) ?: throw ItemNotFoundException("Procedure")
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun findAll(uidLogged: String): Result<List<ProcedureInfo>> {
        return runCatching {
            if (userRepository.findByUid(uidLogged)?.isAdmin != true) throw ForbiddenException()

            procedureRepository.findAll()
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun delete(id: Long, uidLogged: String): Result<Boolean> {
        return runCatching {
            if (userRepository.findByUid(uidLogged)?.isAdmin != true) throw ForbiddenException()

            if (procedureRepository.delete(id)) true else throw ItemNotFoundException("Procedure")
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun update(procedureUpdate: ProcedureUpdate, uidLogged: String): Result<ProcedureInfo> {
        return runCatching {
            if (userRepository.findByUid(uidLogged)?.isAdmin != true) throw ForbiddenException()

            procedureRepository.update(procedureUpdate) ?: throw ItemNotFoundException("Procedure")
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun create(procedureCreate: ProcedureCreate, uidLogged: String): Result<ProcedureInfo> {
        return runCatching {
            if (userRepository.findByUid(uidLogged)?.isAdmin != true) throw ForbiddenException()

            procedureRepository.create(procedureCreate)
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }
}

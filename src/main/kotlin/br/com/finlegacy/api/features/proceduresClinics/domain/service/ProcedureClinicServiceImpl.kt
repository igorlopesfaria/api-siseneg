package br.com.finlegacy.api.features.proceduresClinics.domain.service

import br.com.finlegacy.api.core.exceptions.ForbiddenException
import br.com.finlegacy.api.core.exceptions.GenericServerException
import br.com.finlegacy.api.core.exceptions.ItemNotFoundException
import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.clinics.domain.model.ClinicInfo
import br.com.finlegacy.api.features.clinics.domain.repository.ClinicRepository
import br.com.finlegacy.api.features.procedures.domain.model.ProcedureInfo
import br.com.finlegacy.api.features.procedures.domain.repository.ProcedureRepository
import br.com.finlegacy.api.features.proceduresClinics.domain.model.ProcedureClinicInfo
import br.com.finlegacy.api.features.proceduresClinics.domain.repository.ProcedureClinicRepository
import br.com.finlegacy.api.features.users.domain.repository.UserRepository

class ProcedureClinicServiceImpl(
    private val procedureClinicRepository: ProcedureClinicRepository,
    private val procedureRepository: ProcedureRepository,
    private val userRepository: UserRepository,
    private val clinicRepository: ClinicRepository
) : ProcedureClinicService{

    override suspend fun linkProcedureClinic(procedureId: Long, clinicId: Long, uidLogged: String): Result<ProcedureClinicInfo> {
        return runCatching {
            val loggedUser = userRepository.findByUid(uidLogged) ?: throw ForbiddenException()

            if (!loggedUser.isAdmin) throw ForbiddenException()

            val procedure = procedureRepository.findById(procedureId) ?: throw ItemNotFoundException("Procedure")

            val clinic = clinicRepository.findById(clinicId)
                ?: throw ItemNotFoundException("Clinic")

            procedureClinicRepository.linkProcedureClinic(procedure, clinic)
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun unlinkProcedureClinic(procedureId: Long, clinicId: Long, uidLogged:String): Result<Boolean> {
        return runCatching {
            userRepository.findByUid(uidLogged) ?: throw ForbiddenException()
            procedureRepository.findById(procedureId) ?: throw ItemNotFoundException("Procedure")
            clinicRepository.findById(clinicId) ?: throw ItemNotFoundException("Clinic")

            if(procedureClinicRepository.unlinkProcedureClinic(procedureId, clinicId)) true else throw GenericServerException()
        }.fold(
            onSuccess = {
                Result.Success(it)
            },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun findProceduresByClinicId(clinicId: Long, uidLogged:String): Result<List<ProcedureClinicInfo>> {
        return runCatching {
            val userLogged = userRepository.findByUid(uidLogged) ?: throw ForbiddenException()

            if(!userLogged.isAdmin && userLogged.clinic.id != clinicId) throw ForbiddenException()

            procedureClinicRepository.findProceduresByClinicId(clinicId)
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun findClinicByProcedureId(procedureId: Long, uidLogged:String): Result<List<ProcedureClinicInfo>> {
        return runCatching {
            if (userRepository.findByUid(uidLogged)?.isAdmin != true)
                throw ForbiddenException()
            procedureClinicRepository.findClinicByProcedureId(procedureId)
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }
}
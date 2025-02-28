package br.com.finlegacy.api.features.patients.domain.service

import br.com.finlegacy.api.core.exceptions.ForbiddenException
import br.com.finlegacy.api.core.exceptions.ItemDuplicatedException
import br.com.finlegacy.api.core.exceptions.ItemNotFoundException
import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.maritalStatus.domain.repository.MaritalStatusRepository
import br.com.finlegacy.api.features.patients.domain.model.PatientCreate
import br.com.finlegacy.api.features.patients.domain.model.PatientFilter
import br.com.finlegacy.api.features.patients.domain.model.PatientInfo
import br.com.finlegacy.api.features.patients.domain.model.PatientUpdate
import br.com.finlegacy.api.features.patients.domain.repository.PatientRepository
import br.com.finlegacy.api.features.users.domain.repository.UserRepository

class PatientServiceImpl(
    private val patientRepository: PatientRepository,
    private val userRepository: UserRepository,
    private val maritalStatusRepository: MaritalStatusRepository
): PatientService {
    override suspend fun findById(id: Long, uidLogged: String): Result<PatientInfo> {
        return runCatching {
            val userLogged = userRepository.findByUid(uidLogged)?: throw ItemNotFoundException("User Clinic")

            val patient = patientRepository.findById(id) ?: throw ItemNotFoundException("Patient")

            // Check permissions
            if (!userLogged.isAdmin && userLogged.clinic.id != patient.clinic.id) {
                throw ForbiddenException()
            }

            patient
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun findByFilter(patientFilter: PatientFilter, uidLogged: String): Result<List<PatientInfo>> {
        return runCatching {
            val clinicId: Long = userRepository.findByUid(uidLogged)?.clinic?.id  ?: throw ItemNotFoundException("User clinic")
            patientRepository.findByFilter(patientFilter, clinicId)
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }
    override suspend fun delete(id: Long, uidLogged: String): Result<Boolean> {
        return runCatching {
            val userLogged = userRepository.findByUid(uidLogged)  ?: throw ItemNotFoundException("User clinic")
            val patient = patientRepository.findById(id) ?: throw ItemNotFoundException("Patient")

            // Check permissions
            if (!userLogged.isAdmin && userLogged.clinic.id != patient.clinic.id) {
                throw ForbiddenException()
            }
            patientRepository.delete(id) || throw ItemNotFoundException("Patient")
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }


    override suspend fun update(patientUpdate: PatientUpdate, uidLogged: String): Result<PatientInfo> {
        return runCatching {
            val clinicId = userRepository.findByUid(uidLogged)?.clinic?.id
                ?: throw ItemNotFoundException("User clinic")

            maritalStatusRepository.findById(patientUpdate.martialStatusId)?.id
                ?: throw ItemNotFoundException("Patient marital status")

            suspend fun isDuplicateOnUpdate(id: Long, filter: PatientFilter, errorMessage: String): Result.Failure? {
                val listPatientFiltered = patientRepository.findByFilter(filter, clinicId)
                return if (listPatientFiltered.any { it.id != id }) {
                    Result.Failure(ItemDuplicatedException(errorMessage))
                } else null
            }

            isDuplicateOnUpdate(id = patientUpdate.id, PatientFilter(cpf = patientUpdate.cpf), "CPF")?.let { return it }
            isDuplicateOnUpdate(id = patientUpdate.id, PatientFilter(rg = patientUpdate.rg), "RG")?.let { return it }
            isDuplicateOnUpdate(id = patientUpdate.id, PatientFilter(phoneNumber = patientUpdate.phoneNumber), "phone number")?.let { return it }
            isDuplicateOnUpdate(id = patientUpdate.id, PatientFilter(email = patientUpdate.email), "email")?.let { return it }

            patientRepository.update(patientUpdate, clinicId) ?: throw ItemNotFoundException("Patient")
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }


    override suspend fun create(patientCreate: PatientCreate, uidLogged: String): Result<PatientInfo> {
        return runCatching {
            val clinicId = userRepository.findByUid(uidLogged)?.clinic?.id
                ?: throw ItemNotFoundException("User clinic")

            maritalStatusRepository.findById(patientCreate.martialStatusId)?.id
                ?: throw ItemNotFoundException("Patient marital status")

            suspend fun isDuplicateOnCreate(filter: PatientFilter, errorMessage: String): Result.Failure? {
                return if (patientRepository.findByFilter(filter, clinicId).isNotEmpty()) {
                    Result.Failure(ItemDuplicatedException(errorMessage))
                } else null
            }

            isDuplicateOnCreate(PatientFilter(cpf = patientCreate.cpf), "CPF")?.let { return it }
            isDuplicateOnCreate(PatientFilter(rg = patientCreate.rg), "RG")?.let { return it }
            isDuplicateOnCreate(PatientFilter(phoneNumber = patientCreate.phoneNumber), "phone number")?.let { return it }
            isDuplicateOnCreate(PatientFilter(email = patientCreate.email), "email")?.let { return it }

            patientRepository.create(patientCreate, clinicId)
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

}
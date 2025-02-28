package br.com.finlegacy.api.features.simulations.domain.service

import br.com.finlegacy.api.core.exceptions.ForbiddenException
import br.com.finlegacy.api.core.exceptions.ItemNotFoundException
import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.simulations.domain.model.SimulationCreate
import br.com.finlegacy.api.features.simulations.domain.model.SimulationInfo
import br.com.finlegacy.api.features.simulations.domain.model.SimulationUpdate
import br.com.finlegacy.api.features.patients.domain.repository.PatientRepository
import br.com.finlegacy.api.features.procedures.domain.repository.ProcedureRepository
import br.com.finlegacy.api.features.simulations.domain.repository.SimulationRepository
import br.com.finlegacy.api.features.users.domain.repository.UserRepository

class SimulationServiceImpl(
    private val patientRepository: PatientRepository,
    private val userRepository: UserRepository,
    private val simulationRepository: SimulationRepository
) : SimulationService {

    override suspend fun findById(id: Long, uidLogged: String): Result<SimulationInfo> {
        return runCatching {
            val userLogged = userRepository.findByUid(uidLogged) ?: throw ItemNotFoundException("User Logged")

            val simulation = simulationRepository.findById(id) ?: throw ItemNotFoundException("Simulation")

            // Check permissions
            if (!userLogged.isAdmin && userLogged.clinic.id != simulation.user.clinic.id) {
                throw ForbiddenException()
            }

            simulation
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun findAll(uidLogged: String): Result<List<SimulationInfo>> {
        return runCatching {
            val userLogged = userRepository.findByUid(uidLogged) ?: throw ItemNotFoundException("User Logged")

            if(userLogged.isAdmin) {
               simulationRepository.findAll()
            } else {
                simulationRepository.findAllByClinicId(userLogged.clinic.id)
            }
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun delete(id: Long, uidLogged: String): Result<Boolean> {
        return runCatching {
            val userLogged = userRepository.findByUid(uidLogged)
                ?: throw ItemNotFoundException("User Logged")

            val simulation = simulationRepository.findById(id)
                ?: throw ItemNotFoundException("Simulation")

            // Check permissions
            if (!userLogged.isAdmin && userLogged.clinic.id != simulation.user.clinic.id) {
                throw ForbiddenException()
            }

            // Attempt deletion
            if (simulationRepository.delete(id)) {
                true
            } else {
                throw ItemNotFoundException("Simulation")
            }
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun update(simulationUpdate: SimulationUpdate, uidLogged: String): Result<SimulationInfo> {
        return runCatching {
            val userLogged = userRepository.findByUid(uidLogged)
                ?: throw ItemNotFoundException("User Logged")

            val patient = patientRepository.findById(simulationUpdate.patientId) ?: throw ItemNotFoundException("Patient")
            if (!userLogged.isAdmin && userLogged.clinic.id != patient.clinic.id) {
                throw ForbiddenException()
            }

            val simulation = simulationRepository.findById(simulationUpdate.id)
                ?: throw ItemNotFoundException("Simulation")

            // Check permissions
            if (!userLogged.isAdmin && userLogged.clinic.id != simulation.user.clinic.id) {
                throw ForbiddenException()
            }

            simulationRepository.update(simulationUpdate, userLogged.uid) ?: throw ItemNotFoundException("Simulation")
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun create(simulationCreate: SimulationCreate, uidLogged: String): Result<SimulationInfo> {
        return runCatching {
            val userLogged = userRepository.findByUid(uidLogged)
                ?: throw ItemNotFoundException("User Logged")

            val patient = patientRepository.findById(simulationCreate.patientId) ?: throw ItemNotFoundException("Patient")
            if (!userLogged.isAdmin && userLogged.clinic.id != patient.clinic.id) {
                throw ForbiddenException()
            }
            simulationRepository.create(simulationCreate, uidLogged)
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }
}
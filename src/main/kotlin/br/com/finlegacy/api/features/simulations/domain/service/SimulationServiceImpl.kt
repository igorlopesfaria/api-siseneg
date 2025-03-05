package br.com.finlegacy.api.features.simulations.domain.service

import br.com.finlegacy.api.core.exceptions.ForbiddenException
import br.com.finlegacy.api.core.exceptions.ItemNotFoundException
import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.patients.domain.model.PatientInfo
import br.com.finlegacy.api.features.simulations.domain.model.SimulationCreate
import br.com.finlegacy.api.features.simulations.domain.model.SimulationInfo
import br.com.finlegacy.api.features.simulations.domain.model.SimulationFilter
import br.com.finlegacy.api.features.simulations.domain.repository.SimulationRepository
import br.com.finlegacy.api.features.users.domain.repository.UserRepository

class SimulationServiceImpl(
    private val userRepository: UserRepository,
    private val simulationRepository: SimulationRepository
) : SimulationService {

    override suspend fun findByFilter(simulationFilter: SimulationFilter, uidLogged: String): Result<List<SimulationInfo>> {
        return runCatching {
            val userLogged = userRepository.findByUid(uidLogged) ?: throw ItemNotFoundException("User Logged")

            if (!userLogged.userProfile.isSysAdmin && userLogged.clinic.id != simulationFilter.clinicId) {
                throw ForbiddenException()
            }

            if(!userLogged.userProfile.isSysAdmin) {
                simulationFilter.clinicId = userLogged.clinic.id
            }


            simulationRepository.findByFilter(simulationFilter, uidLogged)

        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun findById(id: Long, uidLogged: String): Result<SimulationInfo> {
        return runCatching {
            val userLogged = userRepository.findByUid(uidLogged)?: throw ItemNotFoundException("User Clinic")

            val simulation = simulationRepository.findById(id) ?: throw ItemNotFoundException("Simulation")

            // Check permissions
            if (!userLogged.userProfile.isSysAdmin && userLogged.clinic.id != simulation.clinicInfo.id) {
                throw ForbiddenException()
            }

            simulation
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun delete(id: Long, uidLogged: String): Result<Boolean> {
        return runCatching {
            val userLogged = userRepository.findByUid(uidLogged) ?: throw ItemNotFoundException("User Logged")

            val simulation  = simulationRepository.findById(id)

            // Check permissions
            if (!userLogged.userProfile.isSysAdmin && userLogged.clinic.id != simulation?.clinicInfo?.id) {
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

    override suspend fun create(simulationCreate: SimulationCreate, uidLogged: String): Result<SimulationInfo> {
        return runCatching {
            val userLogged = userRepository.findByUid(uidLogged) ?: throw ItemNotFoundException("User Logged")

            if (!userLogged.userProfile.isSysAdmin && userLogged.clinic.id != simulationCreate.clinicId) {
                throw ForbiddenException()
            }

            simulationRepository.create(simulationCreate, uidLogged)
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }
}
package br.com.finlegacy.api.features.simulations.domain.repository

import br.com.finlegacy.api.features.simulations.domain.model.SimulationCreate
import br.com.finlegacy.api.features.simulations.domain.model.SimulationInfo
import br.com.finlegacy.api.features.simulations.domain.model.SimulationUpdate

interface SimulationRepository {
    suspend fun findById(id: Long): SimulationInfo?
    suspend fun findAllByClinicId(clinicId: Long): List<SimulationInfo>
    suspend fun findAll(): List<SimulationInfo>
    suspend fun delete(id: Long): Boolean
    suspend fun update(simulationUpdate: SimulationUpdate, uidLogged: String): SimulationInfo?
    suspend fun create(simulationCreate: SimulationCreate, uidLogged: String): SimulationInfo
}
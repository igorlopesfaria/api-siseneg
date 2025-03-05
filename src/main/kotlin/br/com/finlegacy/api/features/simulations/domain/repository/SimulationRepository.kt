package br.com.finlegacy.api.features.simulations.domain.repository

import br.com.finlegacy.api.features.simulations.domain.model.SimulationCreate
import br.com.finlegacy.api.features.simulations.domain.model.SimulationFilter
import br.com.finlegacy.api.features.simulations.domain.model.SimulationInfo

interface SimulationRepository {
    suspend fun findByFilter(simulationFilter: SimulationFilter, uidLogged: String): List<SimulationInfo>
    suspend fun findById(id: Long): SimulationInfo?
    suspend fun delete(id: Long): Boolean
    suspend fun create(simulationCreate: SimulationCreate, uidLogged: String): SimulationInfo
}
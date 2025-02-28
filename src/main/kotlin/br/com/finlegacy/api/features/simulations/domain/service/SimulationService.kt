package br.com.finlegacy.api.features.simulations.domain.service

import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.simulations.domain.model.SimulationCreate
import br.com.finlegacy.api.features.simulations.domain.model.SimulationInfo
import br.com.finlegacy.api.features.simulations.domain.model.SimulationUpdate

interface SimulationService {
    suspend fun findById(id: Long, uidLogged: String): Result<SimulationInfo>
    suspend fun findAll(uidLogged: String): Result<List<SimulationInfo>>
    suspend fun delete(id: Long, uidLogged: String): Result<Boolean>
    suspend fun update(simulationUpdate: SimulationUpdate, uidLogged: String): Result<SimulationInfo>
    suspend fun create(simulationCreate: SimulationCreate, uidLogged: String): Result<SimulationInfo>
}
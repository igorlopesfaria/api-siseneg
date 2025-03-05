package br.com.finlegacy.api.features.simulations.domain.service

import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.simulations.domain.model.SimulationCreate
import br.com.finlegacy.api.features.simulations.domain.model.SimulationFilter
import br.com.finlegacy.api.features.simulations.domain.model.SimulationInfo

interface SimulationService {
    suspend fun findByFilter(simulationFilter: SimulationFilter, uidLogged: String):  Result<List<SimulationInfo>>
    suspend fun findById(id:Long, uidLogged: String):  Result<SimulationInfo>
    suspend fun delete(id: Long, uidLogged: String): Result<Boolean>
    suspend fun create(simulationCreate: SimulationCreate, uidLogged: String): Result<SimulationInfo>
}
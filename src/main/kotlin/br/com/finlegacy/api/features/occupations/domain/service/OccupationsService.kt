package br.com.finlegacy.api.features.occupations.domain.service

import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.occupations.domain.model.OccupationCreate
import br.com.finlegacy.api.features.occupations.domain.model.OccupationInfo
import br.com.finlegacy.api.features.occupations.domain.model.OccupationUpdate

interface OccupationService {
    suspend fun findById(id: Long): Result<OccupationInfo>
    suspend fun findAll(): Result<List<OccupationInfo>>
    suspend fun delete(id: Long, uidLogged: String): Result<Boolean>
    suspend fun update(maritalStatusUpdate: OccupationUpdate, uidLogged: String): Result<OccupationInfo>
    suspend fun create(maritalStatusCreate: OccupationCreate, uidLogged: String): Result<OccupationInfo>
}
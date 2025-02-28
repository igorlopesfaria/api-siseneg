package br.com.finlegacy.api.features.maritalStatus.domain.service

import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.maritalStatus.domain.model.MaritalStatusCreate
import br.com.finlegacy.api.features.maritalStatus.domain.model.MaritalStatusInfo
import br.com.finlegacy.api.features.maritalStatus.domain.model.MaritalStatusUpdate

interface MaritalStatusService {
    suspend fun findById(id: Long): Result<MaritalStatusInfo>
    suspend fun findAll(): Result<List<MaritalStatusInfo>>
    suspend fun delete(id: Long, uidLogged: String): Result<Boolean>
    suspend fun update(maritalStatusUpdate: MaritalStatusUpdate, uidLogged: String): Result<MaritalStatusInfo>
    suspend fun create(maritalStatusCreate: MaritalStatusCreate, uidLogged: String): Result<MaritalStatusInfo>
}
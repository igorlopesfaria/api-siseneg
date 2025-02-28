package br.com.finlegacy.api.features.maritalStatus.domain.repository

import br.com.finlegacy.api.features.maritalStatus.domain.model.MaritalStatusCreate
import br.com.finlegacy.api.features.maritalStatus.domain.model.MaritalStatusInfo
import br.com.finlegacy.api.features.maritalStatus.domain.model.MaritalStatusUpdate

interface MaritalStatusRepository {
    suspend fun findById(id: Long): MaritalStatusInfo?
    suspend fun findAll(): List<MaritalStatusInfo>
    suspend fun delete(id: Long): Boolean
    suspend fun update(maritalStatusUpdate: MaritalStatusUpdate): MaritalStatusInfo?
    suspend fun create(maritalStatusCreate: MaritalStatusCreate): MaritalStatusInfo
}
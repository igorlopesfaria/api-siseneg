package br.com.finlegacy.api.features.occupations.domain.repository

import br.com.finlegacy.api.features.occupations.domain.model.OccupationCreate
import br.com.finlegacy.api.features.occupations.domain.model.OccupationInfo
import br.com.finlegacy.api.features.occupations.domain.model.OccupationUpdate

interface OccupationRepository {
    suspend fun findById(id: Long): OccupationInfo?
    suspend fun findAll(): List<OccupationInfo>
    suspend fun delete(id: Long): Boolean
    suspend fun update(maritalStatusUpdate: OccupationUpdate): OccupationInfo?
    suspend fun create(maritalStatusCreate: OccupationCreate): OccupationInfo
}
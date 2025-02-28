package br.com.finlegacy.api.features.proceduresClinics.domain.model

import br.com.finlegacy.api.features.clinics.domain.model.ClinicInfo
import br.com.finlegacy.api.features.procedures.domain.model.ProcedureInfo
import kotlinx.serialization.Serializable

@Serializable
data class ProcedureClinicInfo (
    val id: Long,
    val clinic: ClinicInfo,
    val procedure: ProcedureInfo,
)

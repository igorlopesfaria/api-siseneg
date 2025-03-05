package br.com.finlegacy.api.features.simulations.domain.model

import br.com.finlegacy.api.features.clinics.domain.model.ClinicInfo
import br.com.finlegacy.api.features.patients.domain.model.PatientInfo
import br.com.finlegacy.api.features.procedures.domain.model.ProcedureInfo
import br.com.finlegacy.api.features.users.domain.model.UserInfo

import kotlinx.serialization.Serializable

@Serializable
data class SimulationInfo (
    val id: Long,
    val simulatedAmount: Double,
    val installments: Int,
    val procedureInfo: ProcedureInfo,
    val patient: PatientInfo,
    val clinicInfo: ClinicInfo,
    val userInfo: UserInfo,
    val createdAt: String
)
package br.com.finlegacy.api.features.simulations.domain.model

import br.com.finlegacy.api.features.patients.domain.model.PatientInfo
import br.com.finlegacy.api.features.proceduresClinics.domain.model.ProcedureClinicInfo
import br.com.finlegacy.api.features.users.domain.model.UserInfo

data class SimulationInfo (
    val id: Long,
    val simulatedAmount: Double,
    val installments: Int,
    val user: UserInfo,
    val procedureClinic: ProcedureClinicInfo,
    val patient: PatientInfo
)
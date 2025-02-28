package br.com.finlegacy.api.features.simulations.domain.model

data class SimulationUpdate (
    val id: Long,
    val simulatedAmount: Double,
    val installments: Int,
    val procedureClinicId: Long,
    val patientId: Long
)
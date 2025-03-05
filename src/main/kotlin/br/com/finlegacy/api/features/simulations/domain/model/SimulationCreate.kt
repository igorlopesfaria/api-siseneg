package br.com.finlegacy.api.features.simulations.domain.model
import kotlinx.serialization.Serializable

@Serializable
data class SimulationCreate (
    val simulatedAmount: Double,
    val installments: Int,
    val procedureId: Long,
    val patientId: Long,
    var clinicId: Long
)
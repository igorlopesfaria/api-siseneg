package br.com.finlegacy.api.features.simulations.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SimulationFilter(
    val id: Long? = null,
    val patientCpf: String? = null,
    val patientName: String? = null,
    val procedureId: Long? = null,
    var clinicId: Long? = null,
)
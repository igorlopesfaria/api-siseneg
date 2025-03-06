package br.com.finlegacy.api.features.clinics.domain.model

import br.com.finlegacy.api.features.address.domain.model.Address
import kotlinx.serialization.Serializable

@Serializable
data class ClinicUpdate (
    val id: Long,
    val name: String,
    val cnpj: String,
    val address: Address,
    val bankId: Long,
    val bankBranchCode: String,
    val bankAccountNumber: String
)
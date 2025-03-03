package br.com.finlegacy.api.features.clinics.domain.model

import br.com.finlegacy.api.features.address.domain.model.AddressInfo
import kotlinx.serialization.Serializable

@Serializable
data class ClinicCreate (
    val name: String,
    val cnpj: String,
    val addressInfo: AddressInfo,
    val bankId: Long,
    val bankBranchCode: String,
    val bankAccountNumber: String
)

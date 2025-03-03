package br.com.finlegacy.api.features.patients.domain.model

import br.com.finlegacy.api.features.address.domain.model.AddressInfo
import kotlinx.serialization.Serializable

@Serializable
data class PatientCreate (
    val fullName: String,
    val cpf: String,
    val rg: String,
    val phoneNumber: String,
    val email: String,
    val birthDate:String,
    val addressInfo: AddressInfo,
    val occupationId: Long,
    val income:Double,
    val martialStatusId: Long,
    val spouseName: String? = null,
    val spouseCpf: String? = null,
)


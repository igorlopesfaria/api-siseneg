package br.com.finlegacy.api.features.patients.domain.model

import br.com.finlegacy.api.features.address.domain.model.AddressInfo
import kotlinx.serialization.Serializable

@Serializable
data class PatientUpdate (
    val id: Long,
    val fullName: String,
    val cpf: String,
    val rg: String,
    val phoneNumber: String,
    val email: String,
    val birthDate:String,
    val addressInfo: AddressInfo,
    val clinicId: Long,
    val occupationId: Long,
    val income:Double,
    val maritalStatusId: Long,
    val spouseName: String? = null,
    val spouseCpf: String? = null,
)
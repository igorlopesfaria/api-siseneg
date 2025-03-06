package br.com.finlegacy.api.features.patients.domain.model

import br.com.finlegacy.api.features.address.domain.model.Address
import kotlinx.serialization.Serializable

@Serializable
data class PatientCreate (
    val fullName: String,
    val cpf: String,
    val rg: String,
    val phoneNumber: String,
    val email: String,
    val birthDate:String,
    val address: Address,
    val clinicId: Long,
    val userId: Long,
    val occupationId: Long,
    val income:Double,
    val maritalStatusId: Long,
    val spouseName: String? = null,
    val spouseCpf: String? = null,
)


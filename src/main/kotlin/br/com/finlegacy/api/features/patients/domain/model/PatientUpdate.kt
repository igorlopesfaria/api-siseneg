package br.com.finlegacy.api.features.patients.domain.model

import br.com.finlegacy.api.features.clinics.domain.model.ClinicInfo
import br.com.finlegacy.api.features.maritalStatus.domain.model.MaritalStatusInfo
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
    val addressCEP: String,
    val addressStreet: String,
    val addressNumber: String,
    val addressNeighborhood: String,
    val addressCity: String,
    val addressState: String,
    val martialStatusId: Long,
    val occupationId: Long,
    val income:Double,
    val spouseName: String? = null,
    val spouseCpf: String? = null,
)

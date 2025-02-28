package br.com.finlegacy.api.features.users.domain.model

import br.com.finlegacy.api.core.extensions.toEncrypt
import kotlinx.serialization.Serializable

@Serializable
data class UserCreate (
    val userName: String,
    val password: String,
    val clinicId: Long
){
    fun encryptPassword(): String {
        return password.toEncrypt()
    }
}
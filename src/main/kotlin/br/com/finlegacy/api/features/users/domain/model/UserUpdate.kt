package br.com.finlegacy.api.features.users.domain.model

import br.com.finlegacy.api.core.extensions.toEncrypt
import kotlinx.serialization.Serializable

@Serializable
data class UserUpdate (
    val uid: String,
    val userName: String,
    val password: String,
){
    fun encryptPassword(): String {
        return password.toEncrypt()
    }
}
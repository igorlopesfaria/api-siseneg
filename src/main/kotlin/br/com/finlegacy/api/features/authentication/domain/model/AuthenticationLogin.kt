package br.com.finlegacy.api.features.authentication.domain.model

import br.com.finlegacy.api.core.extensions.toEncrypt
import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationLogin (
    val userName: String,
    val password: String,
) {
    fun encryptPassword(): String {
        return password.toEncrypt()
    }
}
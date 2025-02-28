package br.com.finlegacy.api.features.users.domain.model

data class UserEmailRecover(
    val email: String,
    val subject: String,
    val body: String,
    val apiKey: String,
    val domain: String
)

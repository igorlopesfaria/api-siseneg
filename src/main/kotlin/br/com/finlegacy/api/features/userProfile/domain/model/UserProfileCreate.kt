package br.com.finlegacy.api.features.userProfile.domain.model

data class UserProfileCreate  (
    val name: String,
    val isSysAdmin: Boolean
)
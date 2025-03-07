package br.com.finlegacy.api.features.userProfiles.controller.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileCreateRequest (
    val name: String,
    val isAdmin: Boolean,
    val isSysAdmin: Boolean
)
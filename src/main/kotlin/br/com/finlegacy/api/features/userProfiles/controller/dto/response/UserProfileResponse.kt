package br.com.finlegacy.api.features.userProfiles.controller.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponse (
    val id: Long,
    val name: String,
    val isAdmin: Boolean,
    val isSysAdmin: Boolean
)
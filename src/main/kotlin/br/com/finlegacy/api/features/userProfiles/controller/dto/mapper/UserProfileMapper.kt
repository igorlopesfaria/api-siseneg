package br.com.finlegacy.api.features.userProfiles.controller.dto.mapper

import br.com.finlegacy.api.features.userProfiles.controller.dto.response.UserProfileResponse
import br.com.finlegacy.api.features.userProfiles.domain.model.UserProfile

fun UserProfile.modelToResponse() = UserProfileResponse(
    id = this.id,
    name = this.name,
    isAdmin = this.isAdmin,
    isSysAdmin = this.isSysAdmin
)
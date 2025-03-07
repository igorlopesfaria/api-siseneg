package br.com.finlegacy.api.features.userProfiles.data

import br.com.finlegacy.api.core.transaction.suspendTransaction
import br.com.finlegacy.api.features.userProfiles.data.entity.UserProfileEntity
import br.com.finlegacy.api.features.userProfiles.data.mapper.entityToModel
import br.com.finlegacy.api.features.userProfiles.controller.dto.request.UserProfileCreateRequest
import br.com.finlegacy.api.features.userProfiles.domain.model.UserProfile
import br.com.finlegacy.api.features.userProfiles.controller.dto.request.UserProfileUpdateRequest
import br.com.finlegacy.api.features.userProfiles.domain.repository.UserProfileRepository
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger

class UserProfileRepositoryImpl: UserProfileRepository {

    override suspend fun findById(id: Long): UserProfile? = suspendTransaction {
        addLogger(StdOutSqlLogger)
        UserProfileEntity.findById(id)?.entityToModel()
    }

    override suspend fun findAll(): List<UserProfile> = suspendTransaction {
        addLogger(StdOutSqlLogger)
        UserProfileEntity.all().toList().map { item ->
            item.entityToModel()
        }
    }

    override suspend fun delete(id: Long): Boolean = suspendTransaction {
        addLogger(StdOutSqlLogger)
        UserProfileEntity.findById(id)?.let {
            it.delete()
            true
        } ?: false
    }

    override suspend fun update(userProfileUpdateRequest: UserProfileUpdateRequest): UserProfile? = suspendTransaction {
        addLogger(StdOutSqlLogger)
        UserProfileEntity.findByIdAndUpdate(userProfileUpdateRequest.id) { item ->
            item.name = userProfileUpdateRequest.name
            item.isAdmin = userProfileUpdateRequest.isAdmin
            item.isSysAdmin = userProfileUpdateRequest.isSysAdmin
        }?.entityToModel()
    }


    override suspend fun create(userProfileCreateRequest: UserProfileCreateRequest): UserProfile = suspendTransaction {
        UserProfileEntity.new {
            name = userProfileCreateRequest.name
            isAdmin = userProfileCreateRequest.isAdmin
            isSysAdmin = userProfileCreateRequest.isSysAdmin
        }.entityToModel()
    }
}
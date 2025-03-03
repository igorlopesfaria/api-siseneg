package br.com.finlegacy.api.features.userProfiles.data

import br.com.finlegacy.api.core.transaction.suspendTransaction
import br.com.finlegacy.api.features.userProfiles.data.entity.UserProfileEntity
import br.com.finlegacy.api.features.userProfiles.data.mapper.entityToModel
import br.com.finlegacy.api.features.userProfiles.domain.model.UserProfileCreate
import br.com.finlegacy.api.features.userProfiles.domain.model.UserProfileInfo
import br.com.finlegacy.api.features.userProfiles.domain.model.UserProfileUpdate
import br.com.finlegacy.api.features.userProfiles.domain.repository.UserProfileRepository
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger

class UserProfileRepositoryImpl: UserProfileRepository {

    override suspend fun findById(id: Long): UserProfileInfo? = suspendTransaction {
        addLogger(StdOutSqlLogger)
        UserProfileEntity.findById(id)?.entityToModel()
    }

    override suspend fun findAll(): List<UserProfileInfo> = suspendTransaction {
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

    override suspend fun update(userProfileUpdate: UserProfileUpdate): UserProfileInfo? = suspendTransaction {
        addLogger(StdOutSqlLogger)
        UserProfileEntity.findByIdAndUpdate(userProfileUpdate.id) { item ->
            item.name = userProfileUpdate.name
            item.isAdmin = userProfileUpdate.isAdmin
            item.isSysAdmin = userProfileUpdate.isSysAdmin
        }?.entityToModel()
    }


    override suspend fun create(userProfileCreate: UserProfileCreate): UserProfileInfo = suspendTransaction {
        UserProfileEntity.new {
            name = userProfileCreate.name
            isAdmin = userProfileCreate.isAdmin
            isSysAdmin = userProfileCreate.isSysAdmin
        }.entityToModel()
    }
}
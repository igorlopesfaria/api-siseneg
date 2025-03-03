package br.com.finlegacy.api.features.userProfile.data

import br.com.finlegacy.api.core.transaction.suspendTransaction
import br.com.finlegacy.api.features.userProfile.data.entity.UserProfileEntity
import br.com.finlegacy.api.features.userProfile.data.mapper.entityToModel
import br.com.finlegacy.api.features.userProfile.domain.model.UserProfileCreate
import br.com.finlegacy.api.features.userProfile.domain.model.UserProfileInfo
import br.com.finlegacy.api.features.userProfile.domain.model.UserProfileUpdate
import br.com.finlegacy.api.features.userProfile.domain.respository.UserProfileRepository
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
            item.isSysAdmin = userProfileUpdate.isSysAdmin
        }?.entityToModel()
    }


    override suspend fun create(userProfileCreate: UserProfileCreate): UserProfileInfo = suspendTransaction {
        UserProfileEntity.new {
            name = userProfileCreate.name
            isSysAdmin = userProfileCreate.isSysAdmin
        }.entityToModel()
    }
}
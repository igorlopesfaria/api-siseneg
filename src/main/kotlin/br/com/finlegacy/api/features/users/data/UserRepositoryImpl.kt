package br.com.finlegacy.api.features.users.data

import br.com.finlegacy.api.core.exceptions.GenericServerException
import br.com.finlegacy.api.core.exceptions.ItemNotFoundException
import br.com.finlegacy.api.core.extensions.toEncrypt
import br.com.finlegacy.api.core.transaction.suspendTransaction
import br.com.finlegacy.api.features.clinics.data.entity.ClinicEntity
import br.com.finlegacy.api.features.users.data.entity.UserEntity
import br.com.finlegacy.api.features.users.data.table.UserTable
import br.com.finlegacy.api.features.users.data.mapper.entityToModel
import br.com.finlegacy.api.features.users.domain.model.UserCreate
import br.com.finlegacy.api.features.users.domain.model.UserInfo
import br.com.finlegacy.api.features.authentication.domain.model.AuthenticationLogin
import br.com.finlegacy.api.features.users.domain.model.UserUpdate
import br.com.finlegacy.api.features.users.domain.repository.UserRepository
import io.ktor.client.*
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.and
import java.util.UUID

class UserRepositoryImpl: UserRepository{

    override suspend fun login(authenticationLogin: AuthenticationLogin): UserInfo? {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            UserEntity.find {
                (UserTable.userName eq authenticationLogin.userName) and (UserTable.password eq authenticationLogin.encryptPassword())
            }.firstOrNull()?.entityToModel() // Return the first matching user or null if no match
        }
    }
    override suspend fun findByUid(uid: String): UserInfo? {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            UserEntity.find {
                UserTable.uid eq uid
            }.firstOrNull()?.entityToModel()
        }
    }

    override suspend fun findByEmail(email: String):UserInfo? {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            UserEntity.find {
                UserTable.userName eq email
            }.firstOrNull()?.entityToModel()
        }
    }



    override suspend fun findAll(): List<UserInfo> {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            UserEntity.all().toList().map { item ->
                item.entityToModel()
            }
        }
    }

    override suspend fun findByUserName(userName: String): UserInfo? {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            UserEntity.find {
                UserTable.userName eq userName
            }.firstOrNull()?.entityToModel()
        }
    }

    override suspend fun delete(uid: String): Boolean {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            val matchingUser = UserEntity.find { UserTable.uid eq uid }.singleOrNull()

            matchingUser?.let { user ->
                user.delete()
                true
            } ?: false
        }
    }

    override suspend fun update(userUpdate: UserUpdate): UserInfo? {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            val matchingUser = UserEntity.find { UserTable.uid eq userUpdate.uid }.singleOrNull()

            matchingUser?.apply {
                userName = userUpdate.userName
                password = userUpdate.encryptPassword()
            }?.entityToModel()

        }
    }

    override suspend fun create(userCreate: UserCreate): UserInfo {
        return suspendTransaction {
            try {
                addLogger(StdOutSqlLogger)

                val clinicEntity = ClinicEntity.findById(userCreate.clinicId)
                    ?: throw ItemNotFoundException("Clinic")

                var newUid: String
                do {
                    newUid = UUID.randomUUID().toString().toEncrypt()
                } while (UserEntity.find { UserTable.uid eq newUid }.firstOrNull() != null)

                UserEntity.new {
                    this.userName = userCreate.userName
                    this.password = userCreate.encryptPassword()
                    this.clinic = clinicEntity
                    this.uid = newUid
                }.entityToModel()
            } catch (e: Exception) {
                throw GenericServerException(e.message)
            }
        }
    }
}
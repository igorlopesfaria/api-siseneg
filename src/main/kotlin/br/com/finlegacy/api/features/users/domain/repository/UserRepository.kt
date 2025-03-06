package br.com.finlegacy.api.features.users.domain.repository

import br.com.finlegacy.api.features.users.domain.model.UserCreate
import br.com.finlegacy.api.features.users.domain.model.UserInfo
import br.com.finlegacy.api.features.users.domain.model.UserEmailRecover
import br.com.finlegacy.api.features.users.domain.model.UserUpdate

interface UserRepository {
    suspend fun login(userName: String, password: String): UserInfo?
    suspend fun findByUserName(userName: String): UserInfo?
    suspend fun findByUid(uid: String): UserInfo?
    suspend fun findByEmail(email: String):UserInfo?
    suspend fun findAll(): List<UserInfo>
    suspend fun delete(uid: String): Boolean
    suspend fun update(userUpdate: UserUpdate): UserInfo?
    suspend fun create(userCreate: UserCreate): UserInfo
}
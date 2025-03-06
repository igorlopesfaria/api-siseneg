package br.com.finlegacy.api.features.users.domain.repository

import br.com.finlegacy.api.features.users.domain.model.UserCreate
import br.com.finlegacy.api.features.users.domain.model.User
import br.com.finlegacy.api.features.users.domain.model.UserUpdate

interface UserRepository {
    suspend fun login(userName: String, password: String): User?
    suspend fun findByUserName(userName: String): User?
    suspend fun findByUid(uid: String): User?
    suspend fun findByEmail(email: String):User?
    suspend fun findAll(): List<User>
    suspend fun delete(uid: String): Boolean
    suspend fun update(userUpdate: UserUpdate): User?
    suspend fun create(userCreate: UserCreate): User
}
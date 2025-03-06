package br.com.finlegacy.api.features.users.domain.service

import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.users.domain.model.UserCreate
import br.com.finlegacy.api.features.users.domain.model.User
import br.com.finlegacy.api.features.users.domain.model.UserUpdate

interface UserService {
    suspend fun delete(uid: String, uidLogged: String): Result<Boolean>
    suspend fun update(userUpdate: UserUpdate, uidLogged: String): Result<User>
    suspend fun create(userCreate: UserCreate, uidLogged: String): Result<User>
    suspend fun findByUid(uid: String, uidLogged: String): Result<User>
    suspend fun findAll( uidLogged: String): Result<List<User>>
    suspend fun sendRecoveryPassword(email: String): Result<Boolean>
}
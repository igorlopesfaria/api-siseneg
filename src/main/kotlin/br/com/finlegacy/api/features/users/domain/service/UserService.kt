package br.com.finlegacy.api.features.users.domain.service

import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.core.worker.EmailMessage
import br.com.finlegacy.api.features.users.domain.model.UserCreate
import br.com.finlegacy.api.features.users.domain.model.UserInfo
import br.com.finlegacy.api.features.procedures.domain.model.ProcedureInfo
import br.com.finlegacy.api.features.users.domain.model.UserUpdate

interface UserService {
    suspend fun delete(uid: String, uidLogged: String): Result<Boolean>
    suspend fun update(userUpdate: UserUpdate, uidLogged: String): Result<UserInfo>
    suspend fun create(userCreate: UserCreate, uidLogged: String): Result<UserInfo>
    suspend fun findByUid(uid: String, uidLogged: String): Result<UserInfo>
    suspend fun findAll( uidLogged: String): Result<List<UserInfo>>
    suspend fun sendRecoveryPassword(email: String): Result<Boolean>
}
package br.com.finlegacy.api.features.users.domain.service

import br.com.finlegacy.api.core.exceptions.ForbiddenException
import br.com.finlegacy.api.core.exceptions.ItemDuplicatedException
import br.com.finlegacy.api.core.exceptions.ItemNotFoundException
import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.core.worker.EmailMessage
import br.com.finlegacy.api.core.worker.EmailWorker
import br.com.finlegacy.api.features.clinics.domain.repository.ClinicRepository
import br.com.finlegacy.api.features.procedures.domain.model.ProcedureInfo
import br.com.finlegacy.api.features.users.domain.model.*
import br.com.finlegacy.api.features.users.domain.repository.UserRepository
import com.rabbitmq.client.Channel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UserServiceImpl(
    private val userRepository: UserRepository,
    private val clinicRepository: ClinicRepository,
    private val channel: Channel
) : UserService {

    private val json = Json { encodeDefaults = true } // Configure JSON serializer
    override suspend fun findByUid(uid: String, uidLogged: String): Result<UserInfo> {
        return runCatching {
            val userLogged = userRepository.findByUid(uidLogged) ?: throw ItemNotFoundException("User Logged")
            if (!userLogged.userProfile.isSysAdmin && userLogged.uid != uid) throw ForbiddenException()
            userRepository.findByUid(uid) ?: throw ItemNotFoundException("User")
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun findAll( uidLogged: String): Result<List<UserInfo>> {
        return runCatching {
            val userLogged = userRepository.findByUid(uidLogged) ?: throw ItemNotFoundException("User Logged")
            if (!userLogged.userProfile.isSysAdmin) throw ForbiddenException()
            userRepository.findAll()
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun sendRecoveryPassword(email: String): Result<Boolean> {
        return runCatching {
            userRepository.findByEmail(email) ?: throw ItemNotFoundException("User")
            val messageJson = json.encodeToString(EmailMessage(
                to = email,
                subject = "Subject",
                text = "Text"
            ))
            // Publish the message to the RabbitMQ queue
            channel.basicPublish("", "emailQueue", null,messageJson.toByteArray())
            true
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun delete(uid: String, uidLogged: String): Result<Boolean> {
        return runCatching {
            val userLogged = userRepository.findByUid(uidLogged) ?: throw ItemNotFoundException("User Logged")
            if (!userLogged.userProfile.isSysAdmin && userLogged.uid != uid) throw ForbiddenException()

            if (userRepository.delete(uid)) true else throw ItemNotFoundException("User")
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun update(userUpdate: UserUpdate, uidLogged: String): Result<UserInfo> {
        return runCatching {
            val userLogged = userRepository.findByUid(uidLogged) ?: throw ItemNotFoundException("Logged user")
            if (!userLogged.userProfile.isSysAdmin && userLogged.uid != userUpdate.uid) throw ForbiddenException()

            userRepository.findByUserName(userUpdate.userName)?.let {
                if (it.uid != userUpdate.uid) throw ItemDuplicatedException("email")
            }
            userRepository.update(userUpdate) ?: throw ItemNotFoundException("User")
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun create(userCreate: UserCreate, uidLogged: String): Result<UserInfo> {
        return runCatching {

            val userLogged = userRepository.findByUid(uidLogged) ?: throw ItemNotFoundException("Logged user")
            if (!userLogged.userProfile.isSysAdmin) throw ForbiddenException()

            userRepository.findByUserName(userCreate.userName)?.let {
                throw ItemDuplicatedException("email")
            }

            userRepository.create(userCreate)
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }
}

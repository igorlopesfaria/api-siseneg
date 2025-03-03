package br.com.finlegacy.api.features.banks.domain.service

import br.com.finlegacy.api.core.exceptions.ForbiddenException
import br.com.finlegacy.api.core.exceptions.ItemNotFoundException
import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.banks.domain.model.BankCreate
import br.com.finlegacy.api.features.banks.domain.model.BankInfo
import br.com.finlegacy.api.features.banks.domain.model.BankUpdate
import br.com.finlegacy.api.features.banks.domain.repository.BankRepository
import br.com.finlegacy.api.features.users.domain.repository.UserRepository

class BankServiceImpl(
    private val bankRepository: BankRepository,
    private val userRepository: UserRepository
) : BankService {

    override suspend fun findById(id: Long, uidLogged: String): Result<BankInfo> {
        return runCatching {
            if (userRepository.findByUid(uidLogged)?.userProfile?.isSysAdmin != true)
                throw ForbiddenException()

            bankRepository.findById(id) ?: throw ItemNotFoundException("Bank")
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun findAll(uidLogged: String): Result<List<BankInfo>> {
        return runCatching {
            if (userRepository.findByUid(uidLogged)?.userProfile?.isSysAdmin != true)
                throw ForbiddenException()

            bankRepository.findAll()
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun delete(id: Long, uidLogged: String): Result<Boolean> {
        return runCatching {
            if (userRepository.findByUid(uidLogged)?.userProfile?.isSysAdmin != true)
                throw ForbiddenException()

            if (bankRepository.delete(id)) {
                true
            } else {
                throw ItemNotFoundException("Bank")
            }
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun update(bankUpdate: BankUpdate, uidLogged: String): Result<BankInfo> {
        return runCatching {
            if (userRepository.findByUid(uidLogged)?.userProfile?.isSysAdmin != true)
                throw ForbiddenException()

            bankRepository.update(bankUpdate) ?: throw ItemNotFoundException("Bank")
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun create(bankCreate: BankCreate, uidLogged: String): Result<BankInfo> {
        return runCatching {
            if (userRepository.findByUid(uidLogged)?.userProfile?.isSysAdmin != true)
                throw ForbiddenException()

            bankRepository.create(bankCreate)
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }
}
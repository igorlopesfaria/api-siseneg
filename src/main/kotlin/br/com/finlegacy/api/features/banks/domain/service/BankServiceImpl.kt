package br.com.finlegacy.api.features.banks.domain.service

import br.com.finlegacy.api.core.exceptions.BadRequestException
import br.com.finlegacy.api.core.exceptions.ForbiddenException
import br.com.finlegacy.api.core.exceptions.ItemNotFoundException
import br.com.finlegacy.api.core.extensions.isValidId
import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.banks.controller.dto.request.BankCreateRequest
import br.com.finlegacy.api.features.banks.domain.model.Bank
import br.com.finlegacy.api.features.banks.controller.dto.request.BankUpdateRequest
import br.com.finlegacy.api.features.banks.domain.repository.BankRepository
import br.com.finlegacy.api.features.users.domain.repository.UserRepository

class BankServiceImpl(
    private val bankRepository: BankRepository,
    private val userRepository: UserRepository
) : BankService {

    override suspend fun findById(id: Long, uidLogged: String): Result<Bank> {
        return runCatching {
            if (!id.isValidId()) throw BadRequestException("Bank id")

            bankRepository.findById(id) ?: throw ItemNotFoundException("Bank")
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun findAll(uidLogged: String): Result<List<Bank>> {
        return runCatching {
            bankRepository.findAll()
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun delete(id: Long, uidLogged: String): Result<Boolean> {
        return runCatching {
            if (userRepository.findByUid(uidLogged)?.userProfile?.isSysAdmin != true) throw ForbiddenException()
            if (!id.isValidId()) throw BadRequestException("Bank id")

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

    override suspend fun update(bankUpdateRequest: BankUpdateRequest, uidLogged: String): Result<Bank> {
        return runCatching {
            if (userRepository.findByUid(uidLogged)?.userProfile?.isSysAdmin != true) throw ForbiddenException()

            if (!bankUpdateRequest.id.isValidId()) throw BadRequestException("Bank id")
            if (bankUpdateRequest.code <= 0) throw BadRequestException("Bank code")
            if (bankUpdateRequest.name.isEmpty()) throw BadRequestException("Bank name")


            bankRepository.update(bankUpdateRequest) ?: throw ItemNotFoundException("Bank")
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun create(bankCreateRequest: BankCreateRequest, uidLogged: String): Result<Bank> {
        return runCatching {
            if (userRepository.findByUid(uidLogged)?.userProfile?.isSysAdmin != true) throw ForbiddenException()
            if (bankCreateRequest.code <= 0) throw BadRequestException("Bank code")
            if (bankCreateRequest.name.isEmpty()) throw BadRequestException("Bank name")

            bankRepository.create(bankCreateRequest)
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }
}
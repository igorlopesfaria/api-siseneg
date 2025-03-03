package br.com.finlegacy.api.features.financialInstitutions.domain.service

import br.com.finlegacy.api.core.exceptions.ForbiddenException
import br.com.finlegacy.api.core.exceptions.ItemNotFoundException
import br.com.finlegacy.api.core.result.Result
import br.com.finlegacy.api.features.financialInstitutions.domain.model.FinancialInstitutionCreate
import br.com.finlegacy.api.features.financialInstitutions.domain.model.FinancialInstitutionInfo
import br.com.finlegacy.api.features.financialInstitutions.domain.model.FinancialInstitutionUpdate
import br.com.finlegacy.api.features.financialInstitutions.domain.repository.FinancialInstitutionRepository
import br.com.finlegacy.api.features.users.domain.repository.UserRepository

class FinancialInstitutionServiceImpl(
    private val financialInstitutionRepository: FinancialInstitutionRepository,
    private val userRepository: UserRepository
) : FinancialInstitutionService {

    override suspend fun findById(id: Long): Result<FinancialInstitutionInfo> {
        return runCatching {
            financialInstitutionRepository.findById(id) ?: throw ItemNotFoundException("Financial Institution")
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun findAll(): Result<List<FinancialInstitutionInfo>> {
        return runCatching {
            financialInstitutionRepository.findAll()
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }


    override suspend fun delete(id: Long, uidLogged: String): Result<Boolean> {
        return runCatching {
            if (userRepository.findByUid(uidLogged)?.userProfile?.isSysAdmin != true)
                throw ForbiddenException()

            if (financialInstitutionRepository.delete(id)) {
                true
            } else {
                throw ItemNotFoundException("Financial Institution")
            }
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun update(financialInstitutionUpdate: FinancialInstitutionUpdate, uidLogged: String): Result<FinancialInstitutionInfo> {

        return runCatching {
            if (userRepository.findByUid(uidLogged)?.userProfile?.isSysAdmin != true)
                throw ForbiddenException()

            financialInstitutionRepository.update(financialInstitutionUpdate) ?: throw ItemNotFoundException("Financial Institution")
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }

    override suspend fun create(financialInstitutionCreate: FinancialInstitutionCreate, uidLogged: String): Result<FinancialInstitutionInfo> {
        return runCatching {
            if (userRepository.findByUid(uidLogged)?.userProfile?.isSysAdmin != true)
                throw ForbiddenException()

            financialInstitutionRepository.create(financialInstitutionCreate)
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Failure(it) }
        )
    }
}

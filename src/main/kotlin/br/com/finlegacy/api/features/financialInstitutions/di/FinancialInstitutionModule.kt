package br.com.finlegacy.api.features.financialInstitutions.di

import br.com.finlegacy.api.features.financialInstitutions.data.FinancialInstitutionRepositoryImpl
import br.com.finlegacy.api.features.financialInstitutions.domain.repository.FinancialInstitutionRepository
import br.com.finlegacy.api.features.financialInstitutions.domain.service.FinancialInstitutionService
import br.com.finlegacy.api.features.financialInstitutions.domain.service.FinancialInstitutionServiceImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val financialInstitutionModule = module {
    single { FinancialInstitutionRepositoryImpl() } bind FinancialInstitutionRepository::class
    single { FinancialInstitutionServiceImpl(get(), get()) } bind FinancialInstitutionService::class
}

package br.com.finlegacy.api.features.banks.di

import br.com.finlegacy.api.features.banks.data.BankRepositoryImpl
import br.com.finlegacy.api.features.banks.domain.repository.BankRepository
import br.com.finlegacy.api.features.banks.domain.service.BankService
import br.com.finlegacy.api.features.banks.domain.service.BankServiceImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val bankModule = module {
    single { BankRepositoryImpl() } bind BankRepository::class
    single { BankServiceImpl(get(), get()) } bind BankService::class
}
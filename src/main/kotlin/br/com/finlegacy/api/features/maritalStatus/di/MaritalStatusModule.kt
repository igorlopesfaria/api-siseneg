package br.com.finlegacy.api.features.maritalStatus.di

import br.com.finlegacy.api.features.maritalStatus.data.MaritalStatusRepositoryImpl
import br.com.finlegacy.api.features.maritalStatus.domain.repository.MaritalStatusRepository
import br.com.finlegacy.api.features.maritalStatus.domain.service.MaritalStatusService
import br.com.finlegacy.api.features.maritalStatus.domain.service.MaritalStatusServiceImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val maritalStatusModule = module {
    single { MaritalStatusRepositoryImpl() } bind MaritalStatusRepository::class
    single { MaritalStatusServiceImpl(get(), get()) } bind MaritalStatusService::class
}
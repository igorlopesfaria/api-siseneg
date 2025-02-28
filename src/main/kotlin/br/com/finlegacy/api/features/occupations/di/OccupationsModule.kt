package br.com.finlegacy.api.features.occupations.di

import br.com.finlegacy.api.features.occupations.data.OccupationRepositoryImpl
import br.com.finlegacy.api.features.occupations.domain.repository.OccupationRepository
import br.com.finlegacy.api.features.occupations.domain.service.OccupationService
import br.com.finlegacy.api.features.occupations.domain.service.OccupationServiceImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val occupationStatusModule = module {
    single { OccupationRepositoryImpl() } bind OccupationRepository::class
    single { OccupationServiceImpl(get(), get()) } bind OccupationService::class
}
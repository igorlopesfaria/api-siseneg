package br.com.finlegacy.api.features.simulations.di

import br.com.finlegacy.api.features.simulations.data.SimulationRepositoryImpl
import br.com.finlegacy.api.features.simulations.domain.repository.SimulationRepository
import br.com.finlegacy.api.features.simulations.domain.service.SimulationService
import br.com.finlegacy.api.features.simulations.domain.service.SimulationServiceImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val simulationModule = module {
    single { SimulationRepositoryImpl() } bind  SimulationRepository::class
    single { SimulationServiceImpl(get(), get(), get()) } bind SimulationService::class

}
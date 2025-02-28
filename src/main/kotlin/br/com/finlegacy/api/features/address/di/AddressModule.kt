package br.com.finlegacy.api.features.address.di

import br.com.finlegacy.api.features.address.data.AddressRepositoryImpl
import br.com.finlegacy.api.features.address.domain.repository.AddressRepository
import br.com.finlegacy.api.features.address.domain.service.AddressService
import br.com.finlegacy.api.features.address.domain.service.AddressServiceImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val addressModule = module {
    single { AddressServiceImpl(get()) } bind AddressService::class
    single { AddressRepositoryImpl(get()) } bind AddressRepository::class

}

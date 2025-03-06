package br.com.finlegacy.api.features.address.di

import br.com.finlegacy.api.features.address.domain.getway.AddressGetway
import br.com.finlegacy.api.features.address.domain.service.AddressService
import br.com.finlegacy.api.features.address.domain.service.AddressServiceImpl
import br.com.finlegacy.api.features.address.getway.correios.CorreiosGetwayImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val addressModule = module {
    single { AddressServiceImpl(get()) } bind AddressService::class
    single { CorreiosGetwayImpl(get()) } bind AddressGetway::class

}

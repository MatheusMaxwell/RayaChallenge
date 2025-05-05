package com.raya.challenge.di

import com.raya.challenge.balance.di.balanceModule
import com.raya.challenge.common.network.networkModule
import org.koin.dsl.module

val appModule = module {
    includes(
        networkModule,
        balanceModule
    )
}
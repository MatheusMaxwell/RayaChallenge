package com.raya.challenge.balance.di

import com.raya.challenge.balance.impl.data.datasource.remote.BalanceDataSourceRemote
import com.raya.challenge.balance.impl.data.datasource.remote.BalanceDataSourceRemoteImpl
import com.raya.challenge.balance.impl.data.datasource.remote.PricesDataSourceRemote
import com.raya.challenge.balance.impl.data.datasource.remote.PricesDataSourceRemoteImpl
import com.raya.challenge.balance.impl.data.repository.BalanceRepositoryImpl
import com.raya.challenge.balance.impl.domain.repository.BalanceRepository
import com.raya.challenge.balance.impl.domain.usecase.ExecuteSwapUseCase
import com.raya.challenge.balance.impl.domain.usecase.GetBalanceTransactionUseCase
import com.raya.challenge.balance.impl.presentation.viewmodel.BalanceViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val balanceModule = module {
    single<BalanceDataSourceRemote> {
        BalanceDataSourceRemoteImpl()
    }
    factory<PricesDataSourceRemote> {
        PricesDataSourceRemoteImpl(httpClient = get())
    }
    factory<BalanceRepository> {
        BalanceRepositoryImpl(
            dataSourceRemote = get(),
            pricesDataSourceRemote = get()
        )
    }
    factory {
        GetBalanceTransactionUseCase(balanceRepository = get())
    }
    factory {
        ExecuteSwapUseCase(balanceRepository = get())
    }

    viewModel {
        BalanceViewModel(
            getBalanceTransactionUseCase = get(),
            executeSwapUseCase = get()
        )
    }
}
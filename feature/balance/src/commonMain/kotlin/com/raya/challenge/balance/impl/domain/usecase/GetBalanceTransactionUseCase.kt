package com.raya.challenge.balance.impl.domain.usecase

import com.raya.challenge.balance.impl.domain.model.BalanceTransactionModel
import com.raya.challenge.balance.impl.domain.repository.BalanceRepository
import com.raya.challenge.common.result.ResponseResult
import com.raya.challenge.common.usecase.BaseUseCase

internal class GetBalanceTransactionUseCase(
    private val balanceRepository: BalanceRepository
): BaseUseCase<Unit, BalanceTransactionModel> {
    override suspend fun invoke(params: Unit?): ResponseResult<BalanceTransactionModel> {
        return balanceRepository.getBalanceTransaction()
    }
}
package com.raya.challenge.balance.impl.data.datasource.remote

import com.raya.challenge.common.result.ResponseResult

internal interface PricesDataSourceRemote {

    suspend fun getPrices(): ResponseResult<Map<String, Map<String, Double>>>
}
package com.raya.challenge.balance.impl.data.datasource.remote

import com.raya.challenge.common.result.ResponseResult
import com.raya.challenge.common.result.error.ResultError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

internal class PricesDataSourceRemoteImpl(
    private val httpClient: HttpClient
): PricesDataSourceRemote {
    override suspend fun getPrices(): ResponseResult<Map<String, Map<String, Double>>> {
        try {
            val resp = httpClient.get("https://api.coingecko.com/api/v3/simple/price") {
                url {
                    parameters.append("ids", "bitcoin,ethereum")
                    parameters.append("vs_currencies", "usd,ars")
                }
            }
            return ResponseResult.Success(resp.body())
        } catch (_: Exception) {
            return ResponseResult.Error(ResultError.ServerError)
        }
    }
}
package com.upstox.assignment.data.repository

import com.upstox.assignment.data.api.HoldingsApiService

class HoldingsRepository(private val apiService: HoldingsApiService) {
    suspend fun getHoldings() = apiService.getHoldings()
}
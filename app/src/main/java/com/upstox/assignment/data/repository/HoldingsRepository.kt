package com.upstox.assignment.data.repository

import com.upstox.assignment.data.api.HoldingsApiService
import com.upstox.assignment.data.model.HoldingsResponse

class HoldingsRepository(private val apiService: HoldingsApiService) {
    suspend fun getHoldings(): HoldingsResponse? {
        return try {
            apiService.getHoldings()
        } catch (e: Exception) {
            null
        }
    }
}
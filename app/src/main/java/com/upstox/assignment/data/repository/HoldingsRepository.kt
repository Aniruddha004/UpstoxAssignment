package com.upstox.assignment.data.repository

import com.upstox.assignment.data.api.HoldingsApiService
import com.upstox.assignment.data.model.HoldingsResponse
import javax.net.ssl.SSLPeerUnverifiedException

class HoldingsRepository(private val apiService: HoldingsApiService) {
    suspend fun getHoldings(): HoldingsResponse? {
        return try {
            apiService.getHoldings()
        } catch (e: Exception) {
            null
        }
    }
}
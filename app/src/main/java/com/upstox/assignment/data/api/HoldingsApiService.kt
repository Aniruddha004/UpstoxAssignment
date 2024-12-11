package com.upstox.assignment.data.api

import com.upstox.assignment.data.model.HoldingsResponse
import retrofit2.http.GET

interface HoldingsApiService {
    @GET("/")
    suspend fun getHoldings(): HoldingsResponse
}
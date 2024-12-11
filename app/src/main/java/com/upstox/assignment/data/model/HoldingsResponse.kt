package com.upstox.assignment.data.model

data class HoldingsResponse(
    val data: HoldingsData
)

data class HoldingsData(
    val userHolding: List<Holding>
)

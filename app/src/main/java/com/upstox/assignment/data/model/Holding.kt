package com.upstox.assignment.data.model

data class Holding(
    val symbol: String = "",
    val quantity: Int = 1,
    val ltp: Double = 0.0,
    val avgPrice: Double = 0.0,
    val close: Double = 0.0
)
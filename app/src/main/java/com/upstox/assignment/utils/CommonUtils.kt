package com.upstox.assignment.utils

import java.text.DecimalFormat

internal fun Double.roundOffDecimal(): Double {
    val df = DecimalFormat("######.00")
    return df.format(this).toDouble()
}
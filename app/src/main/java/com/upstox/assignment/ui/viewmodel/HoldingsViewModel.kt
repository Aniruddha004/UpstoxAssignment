package com.upstox.assignment.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.upstox.assignment.data.model.HoldingsResponse
import com.upstox.assignment.data.repository.HoldingsRepository
import com.upstox.assignment.utils.roundOffDecimal
import com.upstox.assignment.utils.toDecimal2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HoldingsViewModel(private val repository: HoldingsRepository) : ViewModel() {
    private val _holdings = MutableLiveData<HoldingsResponse?>()
    val holdings = liveData(Dispatchers.IO) {
        val retrievedHoldings = repository.getHoldings()
        emit(retrievedHoldings)
    }

    fun getHoldings() {
        viewModelScope.launch {
            val retrievedHoldings = withContext(Dispatchers.IO) {
                repository.getHoldings()
            }
            _holdings.postValue(retrievedHoldings)
        }
    }

    fun calculatePnl(holdings: HoldingsResponse, callback: (Map<String, String>) -> Unit) {
        viewModelScope.launch {
            val pnlData = withContext(Dispatchers.Default) {
                val totalInvestmentDeferred =
                    async { holdings.data.userHolding.sumOf { it.quantity * it.avgPrice } }
                val currentValueDeferred = async { holdings.data.userHolding.sumOf { it.quantity * it.ltp } }
                val daysPnlDeferred =
                    async { holdings.data.userHolding.sumOf { (it.close - it.ltp) * it.quantity } }

                val totalInvestment = totalInvestmentDeferred.await()
                val currentValue = currentValueDeferred.await()
                val totalPnl = currentValue - totalInvestment
                val daysPnl = daysPnlDeferred.await()

                mapOf(
                    "totalPnl" to totalPnl.roundOffDecimal(),
                    "totalInvestment" to totalInvestment.toDecimal2(),
                    "currentValue" to currentValue.toDecimal2(),
                    "daysPnl" to daysPnl.roundOffDecimal()
                )
            }
            callback(pnlData)
        }
    }


}
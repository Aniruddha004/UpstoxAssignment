package com.upstox.assignment.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.upstox.assignment.data.repository.HoldingsRepository
import kotlinx.coroutines.Dispatchers

class HoldingsViewModel(private val repository: HoldingsRepository) : ViewModel() {
    val holdings = liveData(Dispatchers.IO) {
        val retrievedHoldings = repository.getHoldings()
        Log.e("<<<MSG>>>",Gson().toJson(retrievedHoldings))
        emit(retrievedHoldings)
    }
}
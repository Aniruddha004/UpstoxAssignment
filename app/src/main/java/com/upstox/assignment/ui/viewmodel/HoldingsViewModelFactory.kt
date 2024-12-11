package com.upstox.assignment.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.upstox.assignment.data.api.HoldingsApiService
import com.upstox.assignment.data.repository.HoldingsRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HoldingsViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://35dee773a9ec441e9f38d5fc249406ce.api.mockbin.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(HoldingsApiService::class.java)
        val repository = HoldingsRepository(apiService)
        return HoldingsViewModel(repository) as T
    }
}
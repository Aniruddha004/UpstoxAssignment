package com.upstox.assignment.data.repository

import com.upstox.assignment.data.api.HoldingsApiService
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertNull
import org.junit.Test
import org.mockito.Mockito
import retrofit2.HttpException
import retrofit2.Response

class HoldingsRepositoryTest {

    private val mockApiService = Mockito.mock(HoldingsApiService::class.java)
    private val repository = HoldingsRepository(mockApiService)

    @Test
    fun `test getHoldings with SSLPeerUnverifiedException`() = runBlocking {
        val responseBody = "".toResponseBody("application/json".toMediaTypeOrNull())
        val exception = HttpException(Response.error<Any>(500, responseBody))
        Mockito.`when`(mockApiService.getHoldings()).thenThrow(exception)
        val response = repository.getHoldings()
        assertNull(response)
    }

    @Test
    fun `test getHoldings with other Exception`() = runBlocking {
        Mockito.`when`(mockApiService.getHoldings()).thenThrow(RuntimeException::class.java)
        val response = repository.getHoldings()
        assertNull(response)
    }
}
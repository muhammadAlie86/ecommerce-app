package com.example.ecommerce.core.data.repository

import com.example.ecommerce.core.data.remote.models.request.CartRequest
import com.example.ecommerce.core.data.remote.models.response.ChartResponse
import com.example.ecommerce.core.data.remote.service.CartService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChartRepository @Inject constructor(
    val chartService: CartService
){

    suspend fun getAllChart(): List<ChartResponse> = chartService.getAllChart()

    suspend fun getChart(id: Int): ChartResponse = chartService.getChart(id)

    suspend fun updateChart(id: Int,request: CartRequest): ChartResponse = chartService.updateChart(id,request)

    suspend fun addChart(request: CartRequest): ChartResponse = chartService.addChart(request)

    suspend fun deleteChart(id: Int): String = chartService.deleteChart(id)
}



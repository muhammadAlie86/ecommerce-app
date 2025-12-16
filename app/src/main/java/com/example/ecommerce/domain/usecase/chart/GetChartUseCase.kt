package com.example.ecommerce.domain.usecase.chart

import com.example.ecommerce.core.data.repository.ChartRepository
import javax.inject.Inject

class GetChartUseCase @Inject constructor(
    val repository: ChartRepository
) {


}
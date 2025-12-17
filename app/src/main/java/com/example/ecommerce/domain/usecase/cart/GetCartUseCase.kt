package com.example.ecommerce.domain.usecase.chart

import com.example.ecommerce.core.data.repository.CartRepository
import javax.inject.Inject

class GetChartUseCase @Inject constructor(
    val repository: CartRepository
) {


}
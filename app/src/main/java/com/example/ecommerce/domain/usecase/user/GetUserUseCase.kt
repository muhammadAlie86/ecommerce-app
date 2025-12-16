package com.example.ecommerce.domain.usecase.user

import com.example.ecommerce.core.data.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    val repository: UserRepository
) {


}
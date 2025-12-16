package com.example.ecommerce.domain.usecase.user

import com.example.ecommerce.core.data.repository.UserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    val repository: UserRepository
) {


}
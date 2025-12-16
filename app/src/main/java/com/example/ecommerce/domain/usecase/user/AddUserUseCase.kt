package com.example.ecommerce.domain.usecase.user

import com.example.ecommerce.core.data.repository.UserRepository
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    val repository: UserRepository
) {


}
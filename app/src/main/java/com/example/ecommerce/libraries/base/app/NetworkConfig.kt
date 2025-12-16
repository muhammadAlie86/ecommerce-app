package com.example.ecommerce.libraries.base.app

abstract class  NetworkConfig {
    abstract fun baseUrl(): String

    abstract fun timeOut(): Long

    open fun isDev() = false
}
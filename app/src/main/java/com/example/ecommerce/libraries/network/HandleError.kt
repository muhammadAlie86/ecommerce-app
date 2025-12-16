package com.example.ecommerce.libraries.network

import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.net.UnknownServiceException

sealed class Failure : IOException() {
    data class UnknownError(override val message: String?) : Failure()
    data object UnknownHostError : Failure()
    data object EmptyResponse : Failure()
    data object InternetError : Failure()
    data object UnAuthorizedException : Failure()
    data class TimeOutError(override var message: String) : Failure()
    data class ApiError(var code: Int = 0, override var message: String) : Failure()
    data class ServerError(var code: Int = 0, override var message: String) : Failure()
    data class NotFoundException(override var message: String) : Failure()
    data class SocketTimeoutError(override var message: String = "Time Out") : Failure()
    data class BusinessError(override var message: String, val stackTrace: String) : Failure()
    data class HttpError(var code: Int, override var message: String) : Failure()
    data class ConnectivityError(override var message: String = "Perangkat anda tidak tersambung Periksa koneksi dan coba lagi.") : Failure()

}


fun Throwable.handleThrowable(): Failure {
    // Timber.e(this)
    return if (this is UnknownHostException) {
        Failure.ConnectivityError()
    } else if (this is HttpException && this.code() == HttpStatusCode.Unauthorized.code) {
        Failure.UnAuthorizedException
    } else if (this is ConnectException) {
        Failure.ConnectivityError()
    } else if (this is SocketTimeoutException) {
        Failure.SocketTimeoutError()
    } else if (this is SocketException) {
        Failure.ConnectivityError()
    } else if (this is KotlinNullPointerException) {
        Failure.NotFoundException("Data tidak ditemukan")
    } else if (this is UnknownServiceException) {
        Failure.ConnectivityError()
    } else {
        Failure.UnknownError("Permintaan gagal diproses. Harap coba kembali.\n" +
                "Mohon hubungi Helpdesk jika diperlukan.")
    }
}
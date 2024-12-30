package jp.speakbuddy.lib_network.response

sealed class BaseResponse<T> {
    data class Success<T>(val data: T) : BaseResponse<T>()
    data class Failed<T>(val code: Int, val message: String) : BaseResponse<T>()
}

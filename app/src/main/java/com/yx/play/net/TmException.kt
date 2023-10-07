package com.yx.play.net

import com.bumptech.glide.load.HttpException
import java.io.IOException
import java.io.InterruptedIOException
import java.net.*
import javax.net.ssl.SSLException
import javax.net.ssl.SSLHandshakeException
import javax.net.ssl.SSLProtocolException

class TmException : RuntimeException {
    var code: Int? = 0

    constructor(code: Int?, message: String?) : super(message) {
        this.code = code
    }

    constructor(message: String?) : super(message) {
    }

    constructor(error: TmmError) : super(error.message) {
        this.code = error.code
    }

    companion object {
        private val networkExceptionList = mutableListOf(
            UnknownHostException::class.java,
            ConnectException::class.java,
            HttpException::class.java,
            NoRouteToHostException::class.java,
            UnknownHostException::class.java,
            SocketTimeoutException::class.java,
            SSLException::class.java,
            SSLHandshakeException::class.java,
            SSLProtocolException::class.java,
//        NoHttpResponseException::class.java,
            InterruptedIOException::class.java,
            IOException::class.java,
            SocketException::class.java,
//        ClientProtocolException::class.java,
        )


        fun common(): TmException {
            return TmException(
                TmmError.ERROR_COMMON.code,
                TmmError.getDec(TmmError.ERROR_COMMON.code)
            )
        }

//        fun <T> parse(e: Exception): ResponseResult<T?> {
//            return when (e.javaClass) {
//                in networkExceptionList -> {
//                    ResponseResult.Failure(
//                        TmException(
//                            TmmError.ERROR_NETWORK.code,
//                            TmmError.getDec(TmmError.ERROR_NETWORK.code)
//                        )
//                    )
//                }
//
////                {
////                    ResponseResult.Failure(TmException(TmmError.ERROR_NETWORK.code,TmmError.getDec(TmmError.ERROR_NETWORK.code)))
////
////                }
//
//                else -> ResponseResult.Failure(
//                    TmException(
//                        TmmError.ERROR_COMMON.code,
//                        TmmError.getDec(TmmError.ERROR_COMMON.code)
//                    )
//                )
//            }
//        }
    }


}
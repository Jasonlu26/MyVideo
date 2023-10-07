package com.yx.play.net

import android.util.Log
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.yx.play.db.DataBaseManager
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

class NetManager {

    companion object {

        private var instance: NetManager? = null
        @JvmName("getInstance1")
        fun getInstance(): NetManager {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = NetManager()
                    }
                }
            }
            return instance!!
        }
    }
    private var okHttpClient: OkHttpClient? = null

    private fun initOkHttClient() {
        okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(
                LoggingInterceptor.Builder()
                    .setLevel(Level.BASIC)
                    .log(Log.VERBOSE)
                    .build()
            )
            .build()
    }

    fun getOkhttpClient(): OkHttpClient? {
        if (okHttpClient == null) {
            initOkHttClient()
        }
        return okHttpClient
    }

//    {
//        val req =
//            Request.Builder().url(url).addHeader(
//                "token",
//                ApiBaseService.getCurrentToken() ?: ""
//            )
//                .build()
//        if (okHttpClient == null) initUploadConfig()
//        return okHttpClient?.newCall(req)?.execute()
//    }
}
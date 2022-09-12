package com.hola.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * 重试拦截器
 */
class RetryInterceptor(private val mMaxRetryNum: Int) : Interceptor {
    private var mRetryNum = 0

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)
        while ((!response.isSuccessful) && (mRetryNum < mMaxRetryNum)) {
            mRetryNum++
            println("retryNum=$mRetryNum")
            response.close()
            response = chain.proceed(request)
        }
        return response
    }
}
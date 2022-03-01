package com.hola.network

import com.hola.network.converter.JsonConverterFactory
import com.hola.network.interceptor.RetryInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.reflect.ParameterizedType
import java.util.concurrent.TimeUnit

abstract class BaseNetwork<I>(private val mBaseUrl: String) {
    companion object {
        private const val TIME_OUT = 10L
        private const val RETRY_NUM = 3
    }

    val api: I by lazy { getRetrofit().create(getServiceClass()) }

    private fun getServiceClass(): Class<I> {
        val genType = javaClass.genericSuperclass as ParameterizedType
        return genType.actualTypeArguments[0] as Class<I>
    }

    protected open fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(mBaseUrl)
            .client(getOkHttpClient())
            .addConverterFactory(JsonConverterFactory.create(::parserResponseStatus))
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    protected open fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .callTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(RetryInterceptor(RETRY_NUM))
            .build()
    }

    protected open fun parserResponseStatus(result: Any) = Unit
}
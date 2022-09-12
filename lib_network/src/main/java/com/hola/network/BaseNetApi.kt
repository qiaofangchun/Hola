package com.hola.network

import com.hola.network.interceptor.RetryInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.reflect.ParameterizedType
import java.util.concurrent.TimeUnit

abstract class BaseNetApi<I>(private val baseUrl: String) {
    companion object {
        private const val TIME_OUT = 10L
        private const val RETRY_NUM = 3
    }

    val api: I by lazy { getRetrofit().create(getServiceClass()) }

    private fun getServiceClass(): Class<I> {
        val genType = javaClass.genericSuperclass as ParameterizedType
        return genType.actualTypeArguments[0] as Class<I>
    }

    private fun getRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
        configOkHttp(okHttpClient)
        val retrofit = Retrofit.Builder()
        configRetrofit(retrofit)
        return retrofit.baseUrl(baseUrl)
            .client(okHttpClient.build())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    protected open fun configRetrofit(retrofit: Retrofit.Builder) {
        retrofit.addConverterFactory(GsonConverterFactory.create())
    }

    protected open fun configOkHttp(okhttp: OkHttpClient.Builder) {
        okhttp.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .callTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(RetryInterceptor(RETRY_NUM))
            .addInterceptor(HttpLoggingInterceptor())
    }
}
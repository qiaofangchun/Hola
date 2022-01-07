package com.hola.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Proxy
import java.util.concurrent.TimeUnit

abstract class BaseNetworkApi<I>(private val mBaseUrl: String) {
    companion object {
        private const val TIME_OUT = 10L
    }

    val api: I by lazy {
        val apiClz = getServiceClass()
        Proxy.newProxyInstance(
            apiClz.classLoader,
            arrayOf(apiClz),
            ProxyApi(getRetrofit().create(apiClz))
        ) as I
    }

    private fun getServiceClass(): Class<I> {
        val genType = javaClass.genericSuperclass as ParameterizedType
        return genType.actualTypeArguments[0] as Class<I>
    }

    protected open fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(mBaseUrl)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .callTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            })
        configOkHttpClient(builder)
        return builder.build()
    }

    protected open fun configOkHttpClient(builder: OkHttpClient.Builder) {

    }

    inner class ProxyApi(private val api: I) : InvocationHandler {
        override fun invoke(proxy: Any, method: Method, args: Array<Any>): Any? {
            return try {
                method.invoke(api, *args)
            } catch (throwable: Throwable) {
                null
            }
        }
    }
}
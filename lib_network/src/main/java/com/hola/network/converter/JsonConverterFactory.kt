package com.hola.network.converter

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class JsonConverterFactory private constructor(
    private val gson: Gson,
    private val function: ((Any) -> Unit)? = null
) : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type, annotations: Array<Annotation>, retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return JsonResponseBodyConverter(gson, adapter, function)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody> {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return JsonRequestBodyConverter(gson, adapter as TypeAdapter<Any>)
    }

    companion object {
        fun create(): JsonConverterFactory {
            return create(Gson(), null)
        }

        fun create(function: ((Any) -> Unit)?): JsonConverterFactory {
            return JsonConverterFactory(Gson(), function)
        }

        fun create(gson: Gson, function: ((Any) -> Unit)?): JsonConverterFactory {
            return JsonConverterFactory(gson, function)
        }
    }
}
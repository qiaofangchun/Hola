package com.hola.network.converter

import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonToken
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException

internal class JsonResponseBodyConverter(
    private val gson: Gson,
    private val adapter: TypeAdapter<*>,
    private val statusParser: ((Any) -> Unit)? = null
) : Converter<ResponseBody, Any> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): Any {
        val jsonReader = gson.newJsonReader(value.charStream())
        return value.use {
            val result = adapter.read(jsonReader)
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw JsonIOException("JSON document was not fully consumed.")
            }
            statusParser?.invoke(result)
            result
        }
    }
}
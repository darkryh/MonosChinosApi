package com.ead.lib.monoschinos.core.connection

import com.ead.lib.monoschinos.core.client.MonosChinosClient
import com.ead.lib.monoschinos.core.system.extensions.await
import okhttp3.FormBody
import okhttp3.Headers

class RestClient(private val isDebug: Boolean = false) : MonosChinosClient() {

    private val client = httpClient
    private val requestClient = requestHttpClient

    suspend fun request(endPoint: String): String {
        runCatching {
            val response = client
                .newCall(
                    requestClient
                        .newBuilder()
                        .url(BASE_URL + endPoint)
                        .build()
                )
                .await()

            val status = response.code

            val body = response.body

            if (status !in 200..299) {
                if (status in 500..599) {
                    val ex =
                        Exception("An internal server error has occurred, code: $status")
                    if (isDebug) throw ex else exceptionHandler(ex)
                } else {
                    val ex = Exception(
                        "MonosChinos API returns code $status and body $body",
                    )

                    if (isDebug) throw ex
                    else exceptionHandler(ex)
                }
            }

            return body?.string() ?: throw Exception("Empty body")
        }.getOrElse { throwable -> throw throwable }
    }

    suspend fun request(): String {
        runCatching {
            val response = client
                .newCall(
                    requestClient
                        .newBuilder()
                        .url(BASE_URL)
                        .build()
                )
                .await()

            val status = response.code

            val body = response.body

            if (status !in 200..299) {
                if (status in 500..599) {
                    val ex =
                        Exception("An internal server error has occurred, code: $status")
                    if (isDebug) throw ex else exceptionHandler(ex)
                } else {
                    val ex = Exception(
                        "MonosChinos API returns code $status and body $body",
                    )

                    if (isDebug) throw ex
                    else exceptionHandler(ex)
                }
            }

            return body?.string() ?: throw Exception("Empty body")
        }.getOrElse { throwable -> throw throwable }
    }

    suspend fun postRequest(url : String,headers: Headers, body: FormBody): String {
        runCatching {
            val response = client
                .newCall(
                    requestClient
                        .newBuilder()
                        .url(url)
                        .headers(headers)
                        .post(body)
                        .build()
                )
                .await()

            val status = response.code

            val responseBody = response.body

            if (status !in 200..299) {
                if (status in 500..599) {
                    val ex =
                        Exception("An internal server error has occurred, code: $status")
                    if (isDebug) throw ex else exceptionHandler(ex)
                } else {
                    val ex = Exception(
                        "MonosChinos API returns code $status and body $responseBody",
                    )

                    if (isDebug) throw ex
                    else exceptionHandler(ex)
                }
            }

            return responseBody?.string() ?: throw Exception("Empty body")
        }.getOrElse { throwable -> throw throwable }
    }

    private fun exceptionHandler(ex: Exception, message: String? = null) {
        if (message.isNullOrEmpty()) println("Something went wrong! Exception: ${ex.localizedMessage}")
        else println("Something went wrong! Exception: ${ex.localizedMessage}")
    }

    companion object {
        const val BASE_URL = "https://monoschinos2.com/"
    }
}
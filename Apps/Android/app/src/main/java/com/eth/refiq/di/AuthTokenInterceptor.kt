package com.eth.refiq.di

import okhttp3.Interceptor
import okhttp3.Response

class AuthTokenInterceptor : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        val token = API_TOKEN
        req = req.newBuilder().addHeader(AUTH_HEADER, "Bearer $token")
            .build()

        return chain.proceed(req)
    }

    companion object {
        private const val AUTH_HEADER = "Authorization"
    }
}

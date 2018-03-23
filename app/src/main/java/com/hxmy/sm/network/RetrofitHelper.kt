package com.hxmy.sm.network

import com.hxmy.sm.network.services.LoginService
import com.hxmy.sm.network.services.SmService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory


class RetrofitHelper() {


    fun getLoginService(): LoginService {
        val retrofit = createRetrofit()
        return retrofit.create(LoginService::class.java);
    }

    fun getSmService(): SmService {
        val retrofit = createRetrofit()
        return retrofit.create(SmService::class.java);
    }

    private fun createOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()

            val url = originalHttpUrl.newBuilder()
                    //.addQueryParameter("username", "demo")
                    .build()

            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
                    .url(url)

            val request = requestBuilder.build()
            chain.proceed(request)
        }
        return httpClient.build();
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("http://60.205.105.155:9090/message/")
                .addConverterFactory(JsonAndXmlConverters.QualifiedTypeConverterFactory(
                        GsonConverterFactory.create(),
                        SimpleXmlConverterFactory.create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // <- add this
                .client(createOkHttpClient())
                .build()
    }


}
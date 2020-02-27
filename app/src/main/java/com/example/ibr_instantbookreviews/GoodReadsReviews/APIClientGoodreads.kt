package com.example.ibr_instantbookreviews.GoodReadsReviews

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

internal object APIClientGoodreads {

    lateinit var retrofit: Retrofit

    val client: Retrofit
        get() {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .build()


            retrofit = Retrofit.Builder()
                .baseUrl("https://www.goodreads.com/")
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
                .client(client)
                .build()



            return retrofit
        }

}

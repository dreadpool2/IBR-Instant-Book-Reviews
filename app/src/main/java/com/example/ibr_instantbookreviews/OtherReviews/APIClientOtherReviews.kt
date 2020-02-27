package com.example.ibr_instantbookreviews.OtherReviews

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal object APIClientOtherReviews {

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
                .baseUrl("https://sheets.googleapis.com/v4/spreadsheets/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()



            return retrofit
        }

}

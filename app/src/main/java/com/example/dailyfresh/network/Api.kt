package com.example.dailyfresh.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object Api {

public const val API_KEY = "f3ac3e9da92545029b033fda6f3b200d"

private const val BASE_URL = "https://newsapi.org/v2/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build() // this will convert json code into something readable for our project


    //Todo 4(Header) : create a loggin variable
    val logging = HttpLoggingInterceptor()



    //Todo 2(Header): setup Okhttp client with a the api key in the header
    //
    val httpClient = OkHttpClient.Builder().apply { // apply - calls a specific funtion inside this block
        addInterceptor(
            Interceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.header("X-Api-Key", API_KEY)
                // PASSING HEADER INTO THE BUILDER OF THE HTTP CLIENT
                return@Interceptor chain.proceed(builder.build())
            }
        )

        //Todo 5(Header): set up the log to show even the
        // body of the response(before we build the client and after we setup the interceptor)
        // see example
        logging.level = HttpLoggingInterceptor.Level.BODY
        addNetworkInterceptor(logging)


    }.build() // completes building of the http client




    //Todo 3(Header): add httpclient to the retrofit
 private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(httpClient)
        .build()




    val retrofitService : NewsService by lazy {

        retrofit.create(NewsService::class.java)
    }
}
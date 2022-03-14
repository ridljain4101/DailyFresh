package com.example.dailyfresh.network

import com.example.dailyfresh.models.TopNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {



    // we are basically sending requests
    // get me the top articles using parameter apiKey and Country and return calling of TopNewsResponse
    @GET("top-headlines")
    fun getTopArticles(@Query("country") country:String,
    @Query("apiKey") apiKey:String
    ) : Call<TopNewsResponse>


    //Todo 1(Cat. Tab): create get method for getting articles by category(making a request)
    @GET("top-headlines")
    fun getArticlesByCategories(@Query("category") category:String,
                                @Query("apiKey")apiKey: String):Call<TopNewsResponse>



    // TODO 7(Src.) : creating a get method to getarticles by  virtue of  sources(making a request)
@GET("everything")
fun getArticlesBySource(@Query("sources")
source: String)
: Call<TopNewsResponse>




    // TODO 7(Find) : creating a get method to getarticles(making a request)
    @GET("everything")
    fun getArticles(@Query("q")
                           query: String)
            : Call<TopNewsResponse>


}









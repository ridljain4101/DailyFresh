package com.example.dailyfresh.network

import android.util.Log
import androidx.compose.runtime.*
import com.example.dailyfresh.models.ArticleCategory
import com.example.dailyfresh.models.TopNewsResponse
import com.example.dailyfresh.models.getArticleCategory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


//** this class takes care of putting everytinh together and
// we get the response here
// seeration of UI from logic we created already

class NewsManager {

    private val _newsResponse = mutableStateOf(TopNewsResponse())
val newsResponse : State<TopNewsResponse>

@Composable get() = remember {
    _newsResponse
} // this means we have the newsResponse to use it in other classes
    // we need to make it a state and it will update the Ui based on data




    //Todo 2(Cat. Tab): we create a setter  and getter to hold the value from the article by category
   // we can get the data from the other classes but set them only inside this class
    private val _getArticleByCategory =
        mutableStateOf(TopNewsResponse())
    val getArticleByCategory:MutableState<TopNewsResponse>
        @Composable get() = remember {
            _getArticleByCategory
        }


    //Todo 2(Src.): create a variable to keep track of the sourceName
    val sourceName = mutableStateOf("abc-news")


//Todo 8(Src.): create a variable to hold articles by source
private val _getArticleBySource =  mutableStateOf(TopNewsResponse())
    val getArticleBySource :MutableState<TopNewsResponse>
        @Composable get() = remember {
            _getArticleBySource
        }


// TODO 5(Find): creation of a new variable query
val query = mutableStateOf("")


    //todo 8(Find): create setter and getter for the  news response
    // we searched for .
    private val _searchedNewsResponse =
        mutableStateOf(TopNewsResponse())
    val searchedNewsResponse:State<TopNewsResponse>
        @Composable get() = remember {
            _searchedNewsResponse
        }



    //Todo 6(Cat. Scroll): create a variable to keep track of the selected category
    val selectedCategory: MutableState<ArticleCategory?> = mutableStateOf(null)




    init {
    getArticles()
}



    // now we get our articles
private fun getArticles(){
    val service = Api.retrofitService.getTopArticles("us",Api.API_KEY)
service.enqueue(object : Callback<TopNewsResponse>{
// we are not doing it instantly but when app is ready and is going to do it asynchronously and going ton give us the callback/response


    override fun onResponse(call: Call<TopNewsResponse>, response: Response<TopNewsResponse>) {
// if we success. get the articles
        if(response.isSuccessful){
            _newsResponse.value = response.body()!!
            Log.d("error","${_newsResponse.value}")             }// if we get the repsonse display the news in the logcat


        else{
            Log.d("error","${response.errorBody()}")
        }
    }

    override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
        Log.d("error","${t.printStackTrace()}")
    }

}



        )

}

    //Todo 3(Cat.Tab): We create a method to process the request and set the response if its successful
    fun getArticlesByCategory(category: String){
        val client = Api.retrofitService.getArticlesByCategories(category,"")
        client.enqueue(object :Callback<TopNewsResponse>{
            override fun onResponse(call: Call<TopNewsResponse>, response: Response<TopNewsResponse>) {
                if (response.isSuccessful){
                    _getArticleByCategory.value = response.body()!!
                    Log.d("catego.","${_getArticleByCategory.value}")
                }else{
                    Log.d("error","${response.code()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("searcherror","${t.printStackTrace()}")
            }

        })
    }




    //Todo 7(Cat.Scroll): created a method to filter the selected category using its name and pass into the tracking value
    // function triggered whenever selected category is changed
    fun onSelectedCategoryChanged(category:String){
        val newCategory = getArticleCategory(category = category)
        selectedCategory.value = newCategory
    }



    //Todo 9(Src.):process request for articles by source and set to
    fun getArticlesBySource(){
        val service = Api.retrofitService.getArticlesBySource(sourceName.value)
        service.enqueue(object :Callback<TopNewsResponse>{
            override fun onResponse(call: Call<TopNewsResponse>, response: Response<TopNewsResponse>) {
                if (response.isSuccessful){
                    _getArticleBySource.value = response.body()!!
    Log.d("source","${_getArticleBySource.value}")

                }
                else{
                    Log.d("error","${response.code()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {

                    Log.d("error","${t.message}")
                }


        })
    }

    //Todo 9(Find):process the search request and set response to the value holder if successful
    fun getSearchedArticles(query: String){
        val service = Api.retrofitService.getArticles(query)
        service.enqueue(object :Callback<TopNewsResponse>{
            override fun onResponse(call: Call<TopNewsResponse>, response: Response<TopNewsResponse>) {
                if (response.isSuccessful){
                    _searchedNewsResponse.value = response.body()!!
                    Log.d("search","${_searchedNewsResponse.value}")
                }else{
                    Log.d("search","${response.code()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("search error","${t.printStackTrace()}")
            }

        })
    }
}













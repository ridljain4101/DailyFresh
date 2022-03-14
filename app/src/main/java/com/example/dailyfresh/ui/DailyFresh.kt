package com.example.dailyfresh.ui


import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.example.dailyfresh.BottomMenuScreen
import com.example.dailyfresh.MockData
import com.example.dailyfresh.components.BottomMenu
import com.example.dailyfresh.models.TopNewsArticle
import com.example.dailyfresh.network.NewsManager
import com.example.dailyfresh.ui.screen.Categories
import com.example.dailyfresh.ui.screen.DetailScreen
import com.example.dailyfresh.ui.screen.Sources
import com.example.dailyfresh.ui.screen.TopNews

@Composable
fun DailyFresh(){
    val navController = rememberNavController()//handles adding of ComposeNavigator and DialogNavigator
    val scrollState = rememberScrollState()
    //Todo 8: set MainScreen in the DailyFresh
   MainScreen(navController = navController , scrollState)
}

//Todo 1: create a mainScreen composable
@Composable
fun MainScreen(navController: NavHostController, scrollState: ScrollState){
    Scaffold(bottomBar = {
        BottomMenu(navController = navController)
    })
    {        //Todo 9: set Navigation in the MainScree
        Navigation( navController , scrollState, paddingValues = it )
  // 12 TODO add padding values
    }
}


@Composable
fun Navigation(navController: NavHostController, scrollState: ScrollState,newsManager: NewsManager = NewsManager(),paddingValues: PaddingValues) {
    val articles = mutableListOf(TopNewsArticle())
    articles.addAll(
        newsManager.newsResponse.value.articles ?:
        listOf(TopNewsArticle()))

                Log.d("news","$articles" )
// here we get the articles out of the response body which is in form of JSON code
// 10 TODO add let and check if articles are empty and add articles to BN
articles?.let {
    NavHost(
        navController = navController,
        // 11 TODO Change Start Destination
        startDestination =  BottomMenuScreen.TopNews.route, modifier = Modifier.padding(paddingValues = paddingValues)
   //     "TopNews"
    ) {
        //Todo 10(Cat. Scroll): pass in newsManager value
        bottomNavigation(navController = navController, articles,newsManager)

//      13 TODO get rid of it since our default navigation is the startDestination used in BMS
//        composable("TopNews") {
//
//            TopNews(navController = navController) //** naming above doesn't matter we need to pass what actually is the route
//            //** by passing that function here
//            //** confirms navigating to our different screen from this screen(TopNews)
//        }

        //** name of composable doesn't matter
        //** only the file name does while passing it
        //** but the route name used should be same in the other screen's file as well
        //Ex- DetailS here also in TopNews use DetailS only
        // 14 TODO changes newsID to index
        composable("DetailS/{index}"
            //    "{newsId}"
            ,

            arguments = listOf(navArgument("index") { type = NavType.IntType }
            ) // so basically earlier we only had the navController on the DetailScreen which not sufficient
            // so we pass the argument to get the data linked to the news id now
        ) {

            /// here basically we get the newsId and load it in the variable id below

            ////Todo 9 : receive the id from NavBackStackEntry, we use getInt since its of Int type and pass in the key
                navBackStackEntry ->
            val // id
           index = navBackStackEntry.arguments?.getInt("index")
          index?.let {
              if(newsManager.query.value.isNotEmpty()){
                  articles.clear()
                  articles.addAll(newsManager.searchedNewsResponse.value.articles?: listOf())
              }else{
                  articles.clear()
                  articles.addAll(newsManager.newsResponse.value.articles ?: listOf())
              }
              val article = articles[index]

          // 16 TODO moved DetailScreen upwards

              DetailScreen(navController = navController,
                  article
                  , scrollState)
              // 15 TODO newsData to index and id to index



          }


            //Todo 10:pass in the id to getNews created in MockData to retrieve selected news
           // val newsData = MockData.getNews(id)
//
            // Todo 11: provide newsData as a value to detail scree


        }


    }
}
}

// 9 TODO add TNA here and pass it
fun NavGraphBuilder.bottomNavigation(navController: NavHostController, articles : List<TopNewsArticle>,newsManager: NewsManager) {
    composable(BottomMenuScreen.TopNews.route)
    {                                                       // TODO 6(Find) : pass query and news manager here
        TopNews(navController = navController, articles, newsManager.query, newsManager = newsManager )
    // we are creating the TopNews page which needs to know which articles have to be shown which we passed to BottomNavigation before Line 63
    }


    /**Todo 9(Cat.Scroll): create the newsManager variable, pass in the value to Categories and call onSelectedCategoryChanged
     * in onFetchedCategory block and pass in the emitted string.
     *
     */

    composable(BottomMenuScreen.Categories.route)
        {
            //Todo 5(Cat. Tab): set business as default category
            // since we don't want that as soon as we reach the
            // categories tab it is empty
            newsManager.getArticlesByCategory("finance")
            newsManager.onSelectedCategoryChanged("finance")



            Categories(newsManager = newsManager, onFetchCategory ={
                newsManager.onSelectedCategoryChanged(it)
                //it gives us the String and usinng that String in the onSelectedCategoryChanged

//Todo 4 (Cat. Tab): Once we fetch the category, we get the selectCategory(or the one to which we have switched)
// and the articles related to that category
           newsManager.getArticlesByCategory(it)


            } )
        }


            composable(BottomMenuScreen.Sources.route)
            {
                Sources(newsManager = newsManager)
            }

        }



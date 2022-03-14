package com.example.dailyfresh.ui.screen
//** used for a screen which shows the top news/news headlines
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dailyfresh.MockData
import com.example.dailyfresh.MockData.getTimeAgo
import com.example.dailyfresh.NewsData
import com.example.dailyfresh.models.TopNewsArticle
import com.skydoves.landscapist.coil.CoilImage
import com.example.dailyfresh.R
import com.example.dailyfresh.components.SearchBar
import com.example.dailyfresh.network.NewsManager


@Composable
// #5 TODO Pass article of type list of TopNewsArticle

//Todo 3(Find) : create a query variable as a parameter
fun TopNews(navController: NavController,articles : List<TopNewsArticle>,query : MutableState<String>,newsManager: NewsManager){
    
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        //Todo 4(Find): replace Text File with the SearchBar and pass in query as argument
     //   Text(text = "Top News", fontWeight = FontWeight.Bold)
SearchBar(query = query, newsManager = newsManager)

      // TODO 14(FIND) : create a new resultlist variable and check if there is a search word and the search result is not null and add to the list
              /** else add the article from top news and set to the items and TopNewsItem **/
              // Basically creating a new list which either loads the search results or gives the default ones

        val searchedText = query.value
        val resultList = mutableListOf<TopNewsArticle>()
if(searchedText != ""){
    resultList.addAll(newsManager.searchedNewsResponse.value.articles ?: articles)

}else {
    // if the search is empty then the result
        // is just articles
    resultList.addAll(articles)
}

LazyColumn(){
    items(articles.size) // this is the content of the LazyColumn
    // these items will be pieces of MockData
    {
        // now for individual items
       // newsData ->
       // #4 TODO  Make changes here from newsdata to articles
        index ->
        // creation of item for each article in the entire list
        TopNewsItem(article = articles[index] ,
        onNewsClick = {navController.navigate("DetailS/$index")}
            // #21 TODO add onNewsClick here
        )
//        TopNewsItem(, onNewsClick = { // using the method for each item
//            navController.navigate("DetailS/${newsData.id}")
//            // onNewsClick - firstly created for an item then used in a Box clickable funtionality
//            // finally used here for the individual items to go to the detailsscreen
//        })

    }
}



    //   Button(onClick = {
//       navController.navigate("DetailS")
   // ** clicking navigates to the route
//   ) {
//
//       Text(text = "Go to Detail Screen")
//   }




    }
}

@Composable
//#1 TODO replace article with the mockdata we created NewsData
fun TopNewsItem(// newsData: NewsData,
                article: TopNewsArticle, onNewsClick :( ) -> Unit = {}){ // we create a method to be used in .clickable in the box
    Box(modifier = Modifier
        .height(200.dp)
        .padding(8.dp)
        .clickable {
            onNewsClick

        }){

      //  #3 TODO  Add all these properties to the CoilImage
com.skydoves.landscapist.coil.CoilImage(imageModel = article.urlToImage,
contentScale = ContentScale.Crop,
error = ImageBitmap.imageResource(id = R.drawable.panda),
placeHolder = ImageBitmap.imageResource(id = R.drawable.panda)
    )



// 8 TODO  remove CoilImage from below since created above
        //#2 TODO Convert Image to CoilImage since here image is of a diff format
    //   CoilImage(painter = painterResource(id = article. urlToImage), contentDescription = "",
      //  contentScale = ContentScale.FillBounds)

        //#7 TODO make changes here from NewsData.title to articles.title which is a nullable
        Column(modifier = Modifier
            .wrapContentHeight()
            .padding(top = 16.dp, start = 16.dp), verticalArrangement =
        Arrangement.SpaceBetween) { //Arrangement.SpaceBetween - for having space between the contents

            Text(text = MockData.stringToDate(article.publishedAt!!).getTimeAgo(), color = Color.White,
            fontWeight = FontWeight.SemiBold)
            
            Spacer(modifier = Modifier.height(80.dp))
            Text(text = article.title!!,color = Color.White,fontWeight = FontWeight.SemiBold)

        }


    }
    
}





@Preview(showBackground = true)
@Composable
fun TopNewsPreview(){
    // passing the TopNewsItem in the preview with some sample data to preview
    TopNewsItem( // NewsData(
        TopNewsArticle( // 6 TODO pass this here
       // 2,
        author = "Namita Singh",
        title = "Cleo Smith news — live: Kidnap suspect 'in hospital again' as 'hard police grind' credited for breakthrough - The Independent",
        description = "The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.",
        publishedAt = "2021-11-04T04:42:40Z"))
}
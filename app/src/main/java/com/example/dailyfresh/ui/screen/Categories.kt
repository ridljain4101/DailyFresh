package com.example.dailyfresh.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dailyfresh.MockData
import com.example.dailyfresh.MockData.getTimeAgo
import com.example.dailyfresh.R
import com.example.dailyfresh.models.TopNewsArticle
import com.example.dailyfresh.models.getAllArticleCategory
import com.example.dailyfresh.network.NewsManager
import com.skydoves.landscapist.coil.CoilImage


@Composable

//Todo 5(Cat.Scroll): update the Categories screen to display the category tab which earlier only showed text
fun Categories(onFetchCategory:(String)->Unit={},newsManager: NewsManager){
    val tabsItems = getAllArticleCategory()
    Column {
        LazyRow() {
            items(tabsItems.size) {
                val category = tabsItems[it]
                // above concept returns a list of all the cateogories present


                /**Todo 8(Cat.Scroll): Create a newsManager variable,set isSelected value
                 * to selectedCategory value from newsManager == category(see example)
                 *
                 */
                CategoryTab(
                    category = category.categoryName, onFetchCategory = onFetchCategory,
                    isSelected = // setup what category we have got from the newsmanager
                    newsManager.selectedCategory.value == category
                // if the category we are getting for the current tab(from the server) is same as the selected category(from backend)
                // then the current CategoryTab will be selected
                )
            }
        }

        //Todo 6(Cat. Tab): Add in the ArticleContent and pass in getArticleByCategory from newsManager
       // To display the contents in the app rather than in the logcat
        ArticleContent(articles = newsManager.getArticleByCategory.value.articles ?: listOf())

    }
}


// 1 (Cat.Scroll) TODO create a category tab, if selected color is purple 200 otherwise 700
//* @param category keeps track of the selected category
//* @param isSelected is used to change the tab color depending on the value
//* @param onFetchCategory is the action performed when a tab is selected.
@Composable
fun CategoryTab(category: String,
                isSelected: Boolean = false,
                onFetchCategory: (String) -> Unit) {
    val background = if(isSelected) colorResource(id = R.color.purple_200) else colorResource(id =R.color.purple_700)
    Surface(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 16.dp)
            .clickable {
                onFetchCategory(category)
            },
        shape = MaterialTheme.shapes.small,
        color = background
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.body2,
            color = Color.White,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
        )

    }
}



// TODO 1(Cat. UI) : Create a UI of the items of the categories
@Composable
fun ArticleContent(articles : List<TopNewsArticle>, modifier : Modifier = Modifier){
    LazyColumn{
        items(articles){
         article -> Card(modifier.padding(8.dp),
        border = BorderStroke(2.dp , color = colorResource(id = R.color.purple_500))){
// each article is an individual card


             Row(modifier = Modifier
                 .padding(8.dp)
                 .fillMaxWidth()
             ) {
CoilImage(
    imageModel = article.urlToImage,
    modifier = Modifier.size(100.dp),
    placeHolder = painterResource(id = R.drawable.panda),
    error = painterResource(id = R.drawable.panda)


)

                 Column(modifier.padding(8.dp)) {

                     Text(text = article.title ?: "Not Available",
                     fontWeight = FontWeight.Bold,
                     maxLines = 3, overflow = TextOverflow.Ellipsis) // shows ... if data in title is more than 3 lines

                 
                 Row(modifier.fillMaxWidth(),
                 horizontalArrangement = Arrangement.SpaceBetween)
                 {
                   Text(text = article.author?: "Not available")
                 Text(text = MockData.stringToDate(
                     article.publishedAt?: "2021-11-04T01:55:00Z").getTimeAgo())
                // convert date to String
                 }
                 
                 }

                 

             }
    }
    
    
}}}


// TODO 1(Cat. UI) : Creation of a preview of the ArticleContent
@Preview
@Composable
fun ArticleContentPreview(){
    ArticleContent(articles = listOf(TopNewsArticle(
        author = "CBSBoston.com Staff",
        title = "Principal Beaten Unconscious At Dorchester School; Classes Canceled Thursday - CBS Boston",
        description = "Principal Patricia Lampron and another employee were assaulted at Henderson Upper Campus during dismissal on Wednesday.",
        publishedAt = "2021-11-04T01:55:00Z"
    ))
    )
}
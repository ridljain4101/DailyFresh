package com.example.dailyfresh.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.dailyfresh.R
import com.example.dailyfresh.models.TopNewsArticle
import com.example.dailyfresh.network.NewsManager



/**
 * Todo 1(Src.): we create a newsManager variable,
 * create a list for the drop down items
 * Then add a scaffold.
 * */
@Composable
fun Sources(newsManager: NewsManager) {
    val items = listOf(
        "TechCrunch" to "techcrunch",
        "TalkSport" to "talksport",
        "Business Insider" to "business-insider",
        "Reuters" to "reuters",
        "Politico" to "politico",
        "TheVerge" to "the-verge"
    )

    Scaffold(topBar = {
        //Todo 3(Src.): Pass in TopAppBar, set the title to the source name and add drop down as an actions
        TopAppBar(
            title = {
                Text(text = "${newsManager.sourceName.value} Source")
            },
            actions = { // actions in the dropdown
                //Todo 4(Src.): we create a remember variable to control the show and dismiss of the drop down
                var menuExpanded by remember { mutableStateOf(false) }
// will remember show or dismiss of the dropdown


                IconButton(onClick = { menuExpanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = null)
                }

                MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))) {
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = {
                            menuExpanded = false
                        },
                    ) {//Todo 5(Src.): Working on functionality of each of the individual items in the
                        //  dropdown
                        items.forEach { // going for each individual item
                            DropdownMenuItem(onClick = {
                                newsManager.sourceName.value = it.second
                                // on whichever source we click it gets set as sourceName value
                                menuExpanded = false // after selectin an item the dropdown closes
                            }) {
                                Text(it.first)
                                //Todo 6(Src.): Above we passed first and second
                                //**     first will be like example "Reuters"
                                //** and second will be reuters
                                //** first - Displayed in the DropDown - Reuters
                                //** second - passing to the api for the data - reuters


                            }
                        }
                    }
                }
            }
        )
    }
    ) //Todo 6 (Src. UI): Add the Content composable and pass in articles by source
    { // Using source contents inside of the scaffold
        newsManager.getArticlesBySource()
        // loading the individual articles
        val articles = newsManager.getArticleBySource.value
        SourceContent(articles = articles.articles?: listOf() )


    }

}

//Todo 1(Src. UI): Create a composable to display the articles
@Composable
// here we take care of how the UI looks like
fun SourceContent(articles:List<TopNewsArticle>) {
    //Todo 5 (Src. UI): create a Uri Handler
    // enables to handles the uris or URLs
    val uriHandler = LocalUriHandler.current

    //Todo 2(Src. UI) create an annotated string for the articles full URL
    LazyColumn {
        items(articles) { article ->  // getting single articles
               val annotatedString = buildAnnotatedString {
                pushStringAnnotation(
                    tag = "URL",
                    annotation = article.url ?: "newsapi.org"
                )
                //pushing string annotation with a certain style
                withStyle(style = SpanStyle(color = colorResource
                    (id = R.color.purple_500), textDecoration = TextDecoration.Underline)) {
                    // CONTENT OF TEXT DECORATION
                    // refer ss better clarity
                    append("Read Full Article Here")
                }
                pop()
            }

            //Todo 3(Src. UI) : Creating the card
            Card(backgroundColor = colorResource(id = R.color.purple_700),
                elevation = 6.dp,
                modifier = Modifier.padding(8.dp)) {
                Column(modifier = Modifier
                    .height(200.dp)
                    .padding(end = 8.dp, start = 8.dp),
                    verticalArrangement = Arrangement.SpaceEvenly) {
                    Text(
                        text = article.title ?: "Not Available",
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = article.description ?: "Not Available",
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )

                    /**Todo 4(Src. UI) : we use a card to set hyperlink for the url to the full article
                     * We use a ClickableText instead of Text so we can set an action for when its clicked
                     * Pass in the annotated string as text and get its result as a url then open with the uri handler
                     */
                    Card(
                        backgroundColor = colorResource(id = R.color.white),
                        elevation = 6.dp,
                    ) {
                        ClickableText(text = annotatedString,
                            modifier = Modifier.padding(8.dp),
                            onClick = {
                                annotatedString.getStringAnnotations(it,it).firstOrNull()
                                        //  get the first entry or null,
                                    //  if this exists then give me the result
                                    ?.let { result ->
                                        if (result.tag == "URL") {
                                            uriHandler.openUri(result.item)
                                            /// urihandler helps in opening the uri in the
                                            // default source/ magazine from where the article is


                                        }
                                    }
                            })
                    }
                }
            }
        }}}
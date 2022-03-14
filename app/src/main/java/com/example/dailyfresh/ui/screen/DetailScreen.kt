package com.example.dailyfresh.ui.screen
// ** used for a screen which shows details



import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
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
import com.example.dailyfresh.R
import com.example.dailyfresh.models.TopNewsArticle
import com.skydoves.landscapist.coil.CoilImage


@Composable
fun DetailScreen(navController: NavController,
                // newsData: NewsData
    article: TopNewsArticle // 17 TODO do this change
                 ,scrollState: ScrollState
                 // navController: NavController
) {

    Scaffold(topBar = {
DetailTopAppBar(onBackPressed = {navController.popBackStack()})// overriding the onBackPressed adv. function and go to the main screen thaT IS the topnews
    }) {


        Column(modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text = "Detail Screen", fontWeight = FontWeight.Bold)


            // 18 TODO replace image with COILIMAGE
            CoilImage(imageModel = article.urlToImage,
                contentScale = ContentScale.Crop,
                error = ImageBitmap.imageResource(id = R.drawable.panda),
                placeHolder = ImageBitmap.imageResource(id = R.drawable.panda))
            //  Image(painter = painterResource(id = newsData.image), contentDescription = "")

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp), horizontalArrangement = Arrangement.SpaceBetween) {


                // 19 TODO Make all changes from newsData to article
                InfoWithIcon(icon = Icons.Default.Edit, info = article.author ?: "Not available") // if empty return not available
                InfoWithIcon(icon = Icons.Default.DateRange, info = MockData.stringToDate(article.publishedAt!!).getTimeAgo())
                //** We call the string to date method of mockdata which takes the string of published at
                //**we get the date and use how long ago it was published


            }

            Text(article.title ?: "Not available", fontWeight = FontWeight.Bold)
            Text(text =article.description ?: "Not available"
           //  newsData.description
                , modifier = Modifier.padding(top = 16.dp))


            // Button(onClick = {
            //      navController.navigate("TopNews")
//
            navController.popBackStack() //** this helps in closing the app directly after clicking the home button from teh details screen
            //  }) {
            //       Text(text = " Go to TopNews Screen  + ${ newsData.author }")


        }
    }
}




@Composable
fun DetailTopAppBar(onBackPressed : () -> Unit = {}) {
    //** This is also an advanced function just like onNewsClick
    // created above then  used in the onClick of the BackButton and finally overwritten
    //** above to navigate to PopBackStack/MainScreen

    // Always three steps


    TopAppBar(title = { Text(text = "Detail Screen", fontWeight = FontWeight.SemiBold) },
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Arrow Back")
            }
        })
}



@Composable
fun InfoWithIcon(icon: ImageVector, info : String){
Row(){ // a subrow of the above row

    Icon(imageVector = icon, contentDescription = "Author",
        modifier = Modifier.padding(end = 8.dp),
        colorResource(// color of the icon
            id = R.color.purple_500
        )
    )
    Text(text = info)
}

}


@Preview(showBackground = true)
@Composable
fun DetailScreenPreview(){

    DetailScreen(rememberNavController(),
// 20 TODO Make changes from NewsData to passing TNA and removing the source id
        TopNewsArticle(
        // NewsData(

            author = "Namita Singh",
            title = "Cleo Smith news — live: Kidnap suspect 'in hospital again' as 'hard police grind' credited for breakthrough - The Independent",
            description = "The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.",
            publishedAt = "2021-11-04T04:42:40Z"
        ),
        rememberScrollState()
    )
}


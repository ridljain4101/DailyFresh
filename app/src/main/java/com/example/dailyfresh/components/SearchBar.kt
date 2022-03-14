package com.example.dailyfresh.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailyfresh.network.NewsManager

/**Todo 1(Find): create SearchBar composable with a Card and TextField, customizing
 * its keyboard to show the Search icon as the action button
 * Create  @param [query] to keep track of hte query word and get the value
 * from the TextField

 * Todo (Find):  create newsManager variable
 */

@Composable
//
fun SearchBar(query: MutableState<String>, newsManager: NewsManager) {
// TODO 10(Find): create a local focus manager
    val localFocusManager = LocalFocusManager.current
    // takes care of what the focus is of the application

    // TODO 11(FIND) : Creation of the card for the search bar
    Card(elevation = 6.dp,shape = RoundedCornerShape(4.dp),modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        backgroundColor = MaterialTheme.colors.primary) {
        TextField( // we can enter content in it
            value = query.value, onValueChange = {
            query.value  = it
        }, modifier = Modifier
            .fillMaxWidth(),
            label = { // label that helps the user what to input

                Text(text = "Search",color = White)
            }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                // choosing what kind of keyboard to open
                imeAction = ImeAction.Search,
                // shows the search button in the keyboard
            ),
            // puts a search icon in the starting
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, "",tint = White)
            },
            // puts a cancel icon in the end
            trailingIcon = {
                if (query.value != "") {
                    IconButton(
                        onClick = {
                            query.value =
                                ""
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "",
                            tint = White
                        )
                    }
                }
            },
            textStyle = TextStyle(color = White,fontSize = 18.sp),
            keyboardActions = KeyboardActions(
                onSearch = {
                    if (query.value != "") {
                        //Todo 13(FIND): call getSearchArticles when search action is clicked
                        newsManager.getSearchedArticles(query.value)
                    }
                    localFocusManager.clearFocus()
                // clearing the focus,by clicking on the button, the focus is not on entry field anymore
                }
            ),
            colors = TextFieldDefaults.textFieldColors(textColor = White)
        )
    }
}



//Todo 2(Find): create a preview function for the SearchBar
@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    //Todo :pass in NewsManager for preview
    SearchBar(query = mutableStateOf(""),NewsManager())
}
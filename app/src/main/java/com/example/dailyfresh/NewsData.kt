package com.example.dailyfresh

data class NewsData(

    val id : Int,
    val image : Int = R.drawable.live_breaking_news_report_banner,
    val author : String,
    val title : String,
    val description : String,
    val publishedAt : String
)

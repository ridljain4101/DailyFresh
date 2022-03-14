package com.example.dailyfresh.models
//** for creation or defining diff types of categories we want to have

import com.example.dailyfresh.models.ArticleCategory.*


// enum class helps in creating keywords to which we can assign values
//Todo 2(Cat.Scroll): create an enum class for the categories
enum class ArticleCategory(val categoryName:String) {
    BUSINESS("business"),
    ENTERTAINMENT("entertainment"),
    GENERAL("general"),
    HEALTH("health"),
    SCIENCE("science"),
    SPORTS("sports"),
    TECHNOLOGY("technology"),
    FINANCE("finance")
}
//Todo 3(Cat.Scroll): create a list for the categories
fun getAllArticleCategory():List<ArticleCategory>{
    return listOf(ArticleCategory.BUSINESS,
        ArticleCategory.ENTERTAINMENT,
        ArticleCategory.GENERAL,
        ArticleCategory. HEALTH,
        ArticleCategory.  SCIENCE,
        ArticleCategory.SPORTS,
        ArticleCategory. TECHNOLOGY,
        ArticleCategory. FINANCE)
}

//Todo 4(Cat.Scroll): create a method to return a category by its value
// basically getting a specific category
fun getArticleCategory(category: String):ArticleCategory?{
    val map= values().associateBy(ArticleCategory::categoryName)
    return map[category]
}
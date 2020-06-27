package bobby.irawan.newsapp.data.articles.model

import java.io.Serializable

/**
 * Created by bobbyirawan09 on 26/06/20.
 */
data class ArticlesResponse(
    val status: String? = "",
    val totalResults: Int? = 0,
    val articles: List<ArticleResponse>? = listOf(),
    val code: String? = "",
    val error: String? = ""
) : Serializable
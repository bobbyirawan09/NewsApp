package bobby.irawan.newsapp.data.articles.model

import java.io.Serializable

/**
 * Created by bobbyirawan09 on 26/06/20.
 */
data class ArticleErrorResponse(
    val status: String? = "",
    val code: String? = "",
    val message: String? = ""
) : Serializable
package bobby.irawan.newsapp.data.articles.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by bobbyirawan09 on 26/06/20.
 */
data class ArticleResponse(
    val newsSource: ArticleSourceResponse? = null,
    val author: String? = "",
    val title: String? = "",
    val description: String? = "",
    val url: String? = "",
    @SerializedName("urlToImage")
    val urlImage: String? = "",
    val publishedAt: String? = "",
    val content: String? = ""
) : Serializable

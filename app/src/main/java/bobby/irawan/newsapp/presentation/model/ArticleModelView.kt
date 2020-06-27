package bobby.irawan.newsapp.presentation.model

import bobby.irawan.newsapp.data.articles.model.ArticleResponse
import java.io.Serializable

/**
 * Created by bobbyirawan09 on 27/06/20.
 */
data class ArticleModelView(
    var source: String? = "",
    var author: String? = "",
    var title: String? = "",
    var description: String? = "",
    var url: String? = "",
    var urlImage: String? = "",
    var publishedAt: String? = ""
) : Serializable {
    companion object {

        fun from(response: ArticleResponse) = ArticleModelView(
            response.newsSource?.name,
            response.author,
            response.title,
            response.description,
            response.url,
            response.urlImage,
            response.publishedAt
        )
    }
}
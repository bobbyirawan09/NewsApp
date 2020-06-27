package bobby.irawan.newsapp.domain.article

import bobby.irawan.newsapp.utils.Constants

/**
 * Created by bobbyirawan09 on 27/06/20.
 */
interface ArticleRepository {
    suspend fun getArticle(keyword: String, sourceName: String, page: Int): Constants.Response
}
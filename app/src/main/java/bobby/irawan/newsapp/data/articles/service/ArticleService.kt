package bobby.irawan.newsapp.data.articles.service

import bobby.irawan.newsapp.utils.Constants

/**
 * Created by bobbyirawan09 on 26/06/20.
 */
interface ArticleService {

    suspend fun getArticles(keyword: String, sourceName: String, page: Int): Constants.Response
}
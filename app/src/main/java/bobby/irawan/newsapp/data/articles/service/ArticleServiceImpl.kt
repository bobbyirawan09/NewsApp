package bobby.irawan.newsapp.data.articles.service

import bobby.irawan.newsapp.utils.Constants.Response
import com.google.gson.Gson
import javax.inject.Inject

/**
 * Created by bobbyirawan09 on 26/06/20.
 */
class ArticleServiceImpl @Inject constructor(private val api: ArticleApi) :
    ArticleService {
    override suspend fun getArticles(
        keyword: String,
        sourceName: String,
        page: Int
    ): Response {
        val response = api.getArticles(keyword, sourceName, page)
        return if (response.isSuccessful) {
            Response.Success(response.body())
        } else {
            Response.Error(response.message())
        }
    }
}
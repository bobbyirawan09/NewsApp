package bobby.irawan.newsapp.data.articles.service

import bobby.irawan.newsapp.data.articles.model.ArticleErrorResponse
import bobby.irawan.newsapp.utils.Constants.Response
import bobby.irawan.newsapp.utils.orEmpty
import com.google.gson.Gson

/**
 * Created by bobbyirawan09 on 26/06/20.
 */
class ArticleServiceImpl(private val api: ArticleApi, private val gson: Gson) : ArticleService {
    override suspend fun getArticles(
        keyword: String,
        sourceName: String,
        page: Int
    ): Response {
        val response = api.getArticles(keyword, sourceName, page)
        return if (response.isSuccessful) {
            Response.Success(response.body())
        } else {
            val error = gson.fromJson(
                response.errorBody()?.string(),
                ArticleErrorResponse::class.java
            )
            Response.Error(error.message.orEmpty())
        }
    }
}
package bobby.irawan.newsapp.data.articles.service

import bobby.irawan.newsapp.data.articles.model.ArticlesResponse
import bobby.irawan.newsapp.utils.Constants
import bobby.irawan.newsapp.utils.Constants.PATH_EVERYTHING
import bobby.irawan.newsapp.utils.Constants.QUERY_PARAM_API
import bobby.irawan.newsapp.utils.Constants.QUERY_PARAM_PAGE
import bobby.irawan.newsapp.utils.Constants.QUERY_PARAM_SEARCH_TERM
import bobby.irawan.newsapp.utils.Constants.QUERY_PARAM_SOURCES
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by bobbyirawan09 on 26/06/20.
 */
interface ArticleApi {

    @GET(PATH_EVERYTHING)
    suspend fun getArticles(
        @Query(QUERY_PARAM_SEARCH_TERM) keyword: String,
        @Query(QUERY_PARAM_SOURCES) sourceName: String,
        @Query(QUERY_PARAM_PAGE) page: Int,
        @Query(QUERY_PARAM_API) apiKey: String = Constants.API_KEY
    ): Response<ArticlesResponse>
}
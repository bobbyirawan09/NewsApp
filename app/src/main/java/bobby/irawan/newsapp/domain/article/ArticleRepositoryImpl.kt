package bobby.irawan.newsapp.domain.article

import bobby.irawan.newsapp.data.articles.model.ArticlesResponse
import bobby.irawan.newsapp.data.articles.service.ArticleService
import bobby.irawan.newsapp.presentation.model.ArticleModelView
import bobby.irawan.newsapp.utils.Constants.Response

/**
 * Created by bobbyirawan09 on 27/06/20.
 */
class ArticleRepositoryImpl(private val articleService: ArticleService) : ArticleRepository {
    override suspend fun getArticle(keyword: String, sourceName: String, page: Int): Response {
        val convertedReponse: Response
        val response = articleService.getArticles(keyword, sourceName, page)
        when (response) {
            is Response.Success<*> -> {
                val articleResponse = response.data as ArticlesResponse
                convertedReponse = Response.Success(articleResponse.articles?.map {
                    ArticleModelView.from(it)
                })
            }
            is Response.Error -> convertedReponse = Response.Error(response.errorMessage)
        }
        return convertedReponse
    }
}
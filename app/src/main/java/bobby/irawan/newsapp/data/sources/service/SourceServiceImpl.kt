package bobby.irawan.newsapp.data.sources.service

import bobby.irawan.newsapp.utils.Constants.Response
import javax.inject.Inject

/**
 * Created by bobbyirawan09 on 26/06/20.
 */
class SourceServiceImpl @Inject constructor(private val api: SourcesApi) : SourceService {
    override suspend fun getSources(categoryName: String): Response {
        val response = api.getSources(categoryName)
        return if (response.isSuccessful) {
            Response.Success(response.body())
        } else {
            Response.Error(response.message())
        }
    }
}
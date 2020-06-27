package bobby.irawan.newsapp.data.sources.service

import bobby.irawan.newsapp.data.sources.model.SourceErrorResponse
import bobby.irawan.newsapp.utils.Constants.Response
import bobby.irawan.newsapp.utils.orEmpty
import com.google.gson.Gson

/**
 * Created by bobbyirawan09 on 26/06/20.
 */
class SourceServiceImpl(private val api: SourcesApi, private val gson: Gson) : SourceService {
    override suspend fun getSources(categoryName: String): Response {
        val response = api.getSources(categoryName)
        return if (response.isSuccessful) {
            Response.Success(response.body())
        } else {
            val error = gson.fromJson(
                response.errorBody()?.string(),
                SourceErrorResponse::class.java
            )
            Response.Error(error.message.orEmpty())
        }
    }
}
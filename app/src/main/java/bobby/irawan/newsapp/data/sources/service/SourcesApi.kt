package bobby.irawan.newsapp.data.sources.service

import bobby.irawan.newsapp.data.sources.model.SourcesResponse
import bobby.irawan.newsapp.utils.Constants.API_KEY
import bobby.irawan.newsapp.utils.Constants.PATH_SOURCES
import bobby.irawan.newsapp.utils.Constants.QUERY_PARAM_API
import bobby.irawan.newsapp.utils.Constants.QUERY_PARAM_CATEGORY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by bobbyirawan09 on 26/06/20.
 */
interface SourcesApi {

    @GET(PATH_SOURCES)
    suspend fun getSources(
        @Query(QUERY_PARAM_CATEGORY) categoryName: String,
        @Query(QUERY_PARAM_API) apiKey: String = API_KEY
    ): Response<SourcesResponse>
}
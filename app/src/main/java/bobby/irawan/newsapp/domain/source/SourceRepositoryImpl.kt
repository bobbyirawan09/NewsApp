package bobby.irawan.newsapp.domain.source

import bobby.irawan.newsapp.data.sources.model.SourcesResponse
import bobby.irawan.newsapp.data.sources.service.SourceService
import bobby.irawan.newsapp.presentation.model.SourceModelView
import bobby.irawan.newsapp.utils.Constants

/**
 * Created by bobbyirawan09 on 27/06/20.
 */
class SourceRepositoryImpl(private val sourceService: SourceService) : SourceRepository {
    override suspend fun getSource(categoryName: String): Constants.Response {
        val convertedReponse: Constants.Response
        val response = sourceService.getSources(categoryName)
        when (response) {
            is Constants.Response.Success<*> -> {
                val sourceResponse = response.data as SourcesResponse
                convertedReponse = Constants.Response.Success(sourceResponse.sources?.map {
                    SourceModelView.from(it)
                })
            }
            is Constants.Response.Error -> convertedReponse =
                Constants.Response.Error(response.errorMessage)
        }
        return convertedReponse
    }
}
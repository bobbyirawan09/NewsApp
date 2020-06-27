package bobby.irawan.newsapp.data.sources.service

import bobby.irawan.newsapp.utils.Constants

/**
 * Created by bobbyirawan09 on 26/06/20.
 */
interface SourceService {

    suspend fun getSources(categoryName: String): Constants.Response
}
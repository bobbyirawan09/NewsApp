package bobby.irawan.newsapp.domain.source

import bobby.irawan.newsapp.utils.Constants

/**
 * Created by bobbyirawan09 on 27/06/20.
 */
interface SourceRepository {
    suspend fun getSource(categoryName: String): Constants.Response
}
package bobby.irawan.newsapp.utils

import java.util.*

/**
 * Created by bobbyirawan09 on 26/06/20.
 */
object Constants {

    const val BASE_URL = "https://newsapi.org/v2/"
    const val API_KEY = "97876d0094ed46e28aef8d496de74ce0"
    const val PATH_SOURCES = "sources"
    const val QUERY_PARAM_CATEGORY = "category"
    const val PATH_EVERYTHING = "everything"
    const val QUERY_PARAM_SEARCH_TERM = "q"
    const val QUERY_PARAM_SOURCES = "sources"
    const val QUERY_PARAM_PAGE = "page"
    const val QUERY_PARAM_API = "apiKey"
    const val EXTRA_CATEGORY_NAME = "ExtraCategoryName"
    const val EXTRA_SOURCE_ID = "ExtraSourceName"
    const val EXTRA_TITLE = "ExtraTitle"
    const val EXTRA_ARTICLE_URL = "ExtraArticleUrl"
    const val DATE_FORMAT_DEFAULT = "dd-MM-yyyy"
    const val DATE_FORMAT_DAY = "EEEE, dd MMMM yyyy \u2022 HH:mm"
    const val DATE_FORMAT_API = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    const val URL_TEST_NETWORK = "http://www.google.com"
    @JvmField
    val APPS_LOCALE = Locale("in", "ID")

    sealed class Response {
        data class Success<T>(val data: T?) : Response()
        data class Error(val errorMessage: String) : Response()
    }
}
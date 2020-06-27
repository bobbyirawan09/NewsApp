package bobby.irawan.newsapp.data.sources.model

import java.io.Serializable

/**
 * Created by bobbyirawan09 on 26/06/20.
 */
data class SourceResponse(
    val id: String? = "",
    val name: String? = "",
    val description: String? = "",
    val url: String? = "",
    val category: String? = "",
    val language: String? = "",
    val country: String? = ""
) : Serializable
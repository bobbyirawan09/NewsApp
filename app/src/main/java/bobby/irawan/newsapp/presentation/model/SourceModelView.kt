package bobby.irawan.newsapp.presentation.model

import bobby.irawan.newsapp.data.sources.model.SourceResponse
import java.io.Serializable

/**
 * Created by bobbyirawan09 on 27/06/20.
 */
data class SourceModelView(
    var id: String? = "",
    var name: String? = "",
    var url: String? = "",
    var category: String? = "",
    var language: String? = "",
    var country: String? = "",
    var description: String? = ""
) : Serializable {
    companion object {
        fun from(sourceResponse: SourceResponse) = SourceModelView(
            sourceResponse.id,
            sourceResponse.name,
            sourceResponse.url,
            sourceResponse.category,
            sourceResponse.language,
            sourceResponse.country,
            sourceResponse.description
        )
    }
}
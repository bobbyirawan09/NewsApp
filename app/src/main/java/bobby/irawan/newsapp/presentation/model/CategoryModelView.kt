package bobby.irawan.newsapp.presentation.model

import bobby.irawan.newsapp.data.category.model.CategoryEntity
import java.io.Serializable

/**
 * Created by bobbyirawan09 on 26/06/20.
 */
data class CategoryModelView(
    var name: String? = "",
    var image: String? = ""
) : Serializable {
    companion object {
        fun from(entity: CategoryEntity) = CategoryModelView(
            entity.name,
            entity.image
        )
    }
}
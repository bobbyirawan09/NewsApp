package bobby.irawan.newsapp.domain.category

import bobby.irawan.newsapp.presentation.model.CategoryModelView

/**
 * Created by bobbyirawan09 on 27/06/20.
 */
interface CategoryRepository {
    suspend fun getCategories(): List<CategoryModelView>
}
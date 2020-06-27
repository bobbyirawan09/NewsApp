package bobby.irawan.newsapp.data.category.service

import bobby.irawan.newsapp.presentation.model.CategoryModelView

/**
 * Created by bobbyirawan09 on 26/06/20.
 */
interface CategoryService {
    suspend fun getCategories(): List<CategoryModelView>
}
package bobby.irawan.newsapp.domain.category

import bobby.irawan.newsapp.data.category.service.CategoryService
import bobby.irawan.newsapp.presentation.model.CategoryModelView

/**
 * Created by bobbyirawan09 on 27/06/20.
 */
class CategoryRepositoryImpl(private val categoryService: CategoryService) : CategoryRepository {
    override suspend fun getCategories(): List<CategoryModelView> = categoryService.getCategories()
}
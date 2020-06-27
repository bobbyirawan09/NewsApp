package bobby.irawan.newsapp.data.category.service

import bobby.irawan.newsapp.presentation.model.CategoryModelView

/**
 * Created by bobbyirawan09 on 26/06/20.
 */
class CategoryServiceImpl(private val dao: CategoryDao) :
    CategoryService {
    override suspend fun getCategories(): List<CategoryModelView> =
        dao.getCategories().map {
            CategoryModelView.from(it)
        }
}
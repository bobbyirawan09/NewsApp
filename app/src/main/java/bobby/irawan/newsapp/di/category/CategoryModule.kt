package bobby.irawan.newsapp.di.category

import androidx.lifecycle.ViewModel
import bobby.irawan.newsapp.data.category.service.CategoryDao
import bobby.irawan.newsapp.data.category.service.CategoryService
import bobby.irawan.newsapp.data.category.service.CategoryServiceImpl
import bobby.irawan.newsapp.di.viewmodel.ViewModelKey
import bobby.irawan.newsapp.domain.category.CategoryRepository
import bobby.irawan.newsapp.domain.category.CategoryRepositoryImpl
import bobby.irawan.newsapp.presentation.category.viewmodel.CategoryViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

/**
 * Created by bobbyirawan09 on 07/07/20.
 */
@Module
class CategoryModule {

    @Provides
    @IntoMap
    @ViewModelKey(CategoryViewModel::class)
    fun provideCategoryViewModel(viewModel: CategoryViewModel): ViewModel {
        return viewModel
    }

    @CategoryScope
    @Provides
    fun provideCategoryRepository(categoryService: CategoryService) : CategoryRepository {
        return CategoryRepositoryImpl(categoryService)
    }

    @CategoryScope
    @Provides
    fun providesCategoryService(dao: CategoryDao): CategoryService{
        return CategoryServiceImpl(dao)
    }

}
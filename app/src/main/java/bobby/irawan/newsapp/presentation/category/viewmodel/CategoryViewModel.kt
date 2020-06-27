package bobby.irawan.newsapp.presentation.category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bobby.irawan.newsapp.domain.category.CategoryRepository
import bobby.irawan.newsapp.presentation.model.CategoryModelView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by bobbyirawan09 on 27/06/20.
 */
class CategoryViewModel(private val categoryRepository: CategoryRepository) : ViewModel() {
    private val _categoriesLiveData = MutableLiveData<List<CategoryModelView>>()
    val categoriesLiveData: LiveData<List<CategoryModelView>>
        get() = _categoriesLiveData

    fun getCategoryData() {
        viewModelScope.launch(Dispatchers.Main) {
            _categoriesLiveData.postValue(categoryRepository.getCategories())
        }
    }
}
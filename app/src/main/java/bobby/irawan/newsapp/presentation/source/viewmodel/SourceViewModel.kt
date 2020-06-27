package bobby.irawan.newsapp.presentation.source.viewmodel

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bobby.irawan.newsapp.AppController
import bobby.irawan.newsapp.domain.source.SourceRepository
import bobby.irawan.newsapp.presentation.model.SourceModelView
import bobby.irawan.newsapp.utils.Constants.EXTRA_CATEGORY_NAME
import bobby.irawan.newsapp.utils.Constants.Response
import bobby.irawan.newsapp.utils.isInternetConnected
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by bobbyirawan09 on 27/06/20.
 */

class SourceViewModel(private val sourceRepository: SourceRepository) : ViewModel() {

    private var sources = listOf<SourceModelView>()
    private var categoryName: String = ""

    private val _sourcesLiveData = MutableLiveData<List<SourceModelView>>()
    val sourcesLiveData: LiveData<List<SourceModelView>>
        get() = _sourcesLiveData

    private val _filterSourcesLiveData = MutableLiveData<List<SourceModelView>>()
    val filterSourcesLiveData: LiveData<List<SourceModelView>>
        get() = _filterSourcesLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    private val _titleLiveData = MutableLiveData<String>()
    val titleLiveData: LiveData<String>
        get() = _titleLiveData

    fun getBundle(intent: Intent?) {
        if (intent != null) {
            categoryName = intent.getStringExtra(EXTRA_CATEGORY_NAME) ?: ""
            _titleLiveData.postValue(categoryName)
            getSourceData(categoryName)
        }
    }

    fun getSourceData(categoryName: String) {
        if (AppController.getInstance().isInternetConnected()) {
            viewModelScope.launch(Dispatchers.Main) {
                val response = sourceRepository.getSource(categoryName)
                when (response) {
                    is Response.Success<*> -> {
                        sources = (response.data as List<SourceModelView>)
                        _sourcesLiveData.postValue(sources)
                    }
                    is Response.Error -> _messageLiveData.postValue(response.errorMessage)
                }
            }
        } else {
            _messageLiveData.postValue("No Internet Connection Available")
        }
    }

    fun onFilterSourceData(keyword: String) {
        if (keyword.isEmpty()) {
            _filterSourcesLiveData.postValue(sources)
        } else {
            val filterSources = sources.filter {
                it.name?.contains(keyword, ignoreCase = true) ?: false
            }
            _filterSourcesLiveData.postValue(filterSources)
        }
    }

}
package bobby.irawan.newsapp.presentation.article.viewmodel

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bobby.irawan.newsapp.AppController
import bobby.irawan.newsapp.domain.article.ArticleRepository
import bobby.irawan.newsapp.presentation.model.ArticleModelView
import bobby.irawan.newsapp.utils.Constants
import bobby.irawan.newsapp.utils.isInternetConnected
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by bobbyirawan09 on 27/06/20.
 */

class ArticleViewModel @Inject constructor(private val articleRepository: ArticleRepository) :
    ViewModel() {
    private var articles = mutableListOf<ArticleModelView>()
    private var sourceName: String = ""
    private var title = ""
    private var isLoadingNextPage = false
    private var isNextPageAvailable = true
    private var page = 1

    private val _articlesLiveData = MutableLiveData<MutableList<ArticleModelView>>()
    val articlesLiveData: LiveData<MutableList<ArticleModelView>>
        get() = _articlesLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    private val _titleLiveData = MutableLiveData<String>()
    val titleLiveData: LiveData<String>
        get() = _titleLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData

    private val _loadingNextPageLiveData = MutableLiveData<Boolean>()
    val loadingNextPageLiveData: LiveData<Boolean>
        get() = _loadingNextPageLiveData

    private val _articlesNextPageLiveData = MutableLiveData<MutableList<ArticleModelView>>()
    val articlesNextPageLiveData: LiveData<MutableList<ArticleModelView>>
        get() = _articlesNextPageLiveData

    fun getBundle(intent: Intent?) {
        if (intent != null) {
            sourceName = intent.getStringExtra(Constants.EXTRA_SOURCE_ID) ?: ""
            title = intent.getStringExtra(Constants.EXTRA_TITLE) ?: ""
            _titleLiveData.postValue(title)
        }
    }

    fun getArticleData(keyword: String, isInternetConnected: Boolean) {
        _loadingLiveData.postValue(true)
        page = 1
        if (isInternetConnected) {
            viewModelScope.launch(Dispatchers.Main) {
                val response = articleRepository.getArticle(keyword, sourceName, page)
                when (response) {
                    is Constants.Response.Success<*> -> {
                        articles = (response.data as MutableList<ArticleModelView>)
                        _articlesLiveData.postValue(articles)
                        page++
                        if (articles.size == 0) {
                            _messageLiveData.postValue("Article(s) Not Found")
                        }
                    }
                    is Constants.Response.Error -> {
                        isNextPageAvailable = false
                        _messageLiveData.postValue(response.errorMessage)
                    }
                }
            }
        } else {
            _messageLiveData.postValue("No Internet Connection Available")
        }
    }

    fun getNextArticleData(isInternetConnected: Boolean) {
        if (!isLoadingNextPage && isNextPageAvailable && isInternetConnected
        ) {
            isLoadingNextPage = true
            _loadingNextPageLiveData.postValue(true)
            viewModelScope.launch(Dispatchers.Main) {
                val response = articleRepository.getArticle("bitcoin", sourceName, page)
                when (response) {
                    is Constants.Response.Success<*> -> {
                        val tempArticles = (response.data as MutableList<ArticleModelView>)
                        articles.addAll(tempArticles)
                        _articlesNextPageLiveData.postValue(tempArticles)
                        page++
                        isLoadingNextPage = false
                    }
                    is Constants.Response.Error -> {
                        isNextPageAvailable = false
                        _messageLiveData.postValue(response.errorMessage)
                    }
                }
            }
        }
    }
}
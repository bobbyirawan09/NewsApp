package bobby.irawan.newsapp.presentation.article.viewmodel

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bobby.irawan.newsapp.utils.Constants

/**
 * Created by bobbyirawan09 on 27/06/20.
 */
class ArticleDetailViewModel : ViewModel() {

    private var title = ""
    private var articleUrl = ""

    private val _titleLiveData = MutableLiveData<String>()
    val titleLiveData: LiveData<String>
        get() = _titleLiveData

    private val _articleUrlLiveData = MutableLiveData<String>()
    val articleUrlLiveData: LiveData<String>
        get() = _articleUrlLiveData

    fun getBundle(intent: Intent?) {
        if (intent != null) {
            articleUrl = intent.getStringExtra(Constants.EXTRA_ARTICLE_URL) ?: ""
            title = intent.getStringExtra(Constants.EXTRA_TITLE) ?: ""
            _titleLiveData.postValue(title)
            _articleUrlLiveData.postValue(articleUrl)
        }
    }
}
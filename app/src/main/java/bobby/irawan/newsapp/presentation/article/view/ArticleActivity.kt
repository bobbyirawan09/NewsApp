package bobby.irawan.newsapp.presentation.article.view

import android.R
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import bobby.irawan.newsapp.AppController
import bobby.irawan.newsapp.databinding.ActivityArticleBinding
import bobby.irawan.newsapp.di.viewmodel.ViewModelProviderFactory
import bobby.irawan.newsapp.presentation.article.adapter.ArticleAdapter
import bobby.irawan.newsapp.presentation.article.viewmodel.ArticleViewModel
import bobby.irawan.newsapp.presentation.model.ArticleModelView
import bobby.irawan.newsapp.utils.Constants.EXTRA_SOURCE_ID
import bobby.irawan.newsapp.utils.Constants.EXTRA_TITLE
import bobby.irawan.newsapp.utils.isInternetConnected
import bobby.irawan.newsapp.utils.setGone
import bobby.irawan.newsapp.utils.setVisible
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout.END_ICON_CLEAR_TEXT
import javax.inject.Inject

class ArticleActivity : AppCompatActivity(), ArticleAdapter.ClickListener,
    TextView.OnEditorActionListener {
    private lateinit var binding: ActivityArticleBinding
    lateinit var articleViewModel: ArticleViewModel

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory
    private val adapter by lazy {
        ArticleAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        val articleComponent =
            (application as AppController).appComponent.articleComponent().create()
        articleComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        articleViewModel =
            ViewModelProvider(this, providerFactory).get(ArticleViewModel::class.java)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        onInitUi()
        articleViewModel.getBundle(intent)
        observerViewModel()
    }

    private fun onInitUi() {
        adapter.setClickListener(this)
        binding.recyclerViewArticle.setHasFixedSize(true)
        binding.recyclerViewArticle.adapter = adapter
        binding.textInputLayoutSearchArticle.endIconMode = END_ICON_CLEAR_TEXT
        binding.textInputLayoutSearchArticle.editText?.setOnEditorActionListener(this)
    }

    private fun onSetLoadingState() {
        binding.recyclerViewArticle.setGone()
        binding.progressBar.setVisible()
        binding.textInputLayoutSearchArticle.isEnabled = false
    }

    override fun onNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun observerViewModel() {
        articleViewModel.articlesLiveData.observe(this, Observer {
            onSetResultState(it)
        })
        articleViewModel.messageLiveData.observe(this, Observer {
            onSetErrorState(it)
        })
        articleViewModel.titleLiveData.observe(this, Observer {
            supportActionBar?.title = it
        })
        articleViewModel.loadingLiveData.observe(this, Observer {
            onSetLoadingState()
        })
        articleViewModel.loadingNextPageLiveData.observe(this, Observer {
            onSetLoadingNextPageState()
        })
        articleViewModel.articlesNextPageLiveData.observe(this, Observer {
            onSetResultNextPageState(it)
        })
    }

    private fun onSetErrorState(message: String?) {
        binding.recyclerViewArticle.setVisible()
        binding.progressBar.setGone()
        binding.textInputLayoutSearchArticle.isEnabled = true
        binding.linearLayoutLoading.setGone()
        Snackbar.make(binding.root, message.orEmpty(), Snackbar.LENGTH_LONG)
            .show()
    }

    private fun onSetResultState(articles: MutableList<ArticleModelView>) {
        adapter.setArticles(articles)
        binding.recyclerViewArticle.setVisible()
        binding.progressBar.setGone()
        binding.textInputLayoutSearchArticle.isEnabled = true
    }

    private fun onSetResultNextPageState(articles: MutableList<ArticleModelView>) {
        adapter.updateList(articles)
        binding.linearLayoutLoading.setGone()
    }

    private fun onSetLoadingNextPageState() {
        binding.linearLayoutLoading.setVisible()
    }

    override fun onLoadNextPage() {
        articleViewModel.getNextArticleData(
            AppController.getInstance()
                .isInternetConnected()
        )
    }

    override fun onClick(article: ArticleModelView) {
        ArticleDetailActivity.startActivity(this, article.url.orEmpty(), article.title.orEmpty())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
        if (p1 == IME_ACTION_DONE) {
            val imm: InputMethodManager =
                applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(p0?.windowToken, 0)
            articleViewModel.getArticleData(p0?.text.toString(), AppController.getInstance().isInternetConnected())
            return true
        }
        return false
    }

    companion object {
        fun startActivity(activity: Activity, sourceName: String, title: String) {
            activity.startActivity(
                Intent(
                    activity, ArticleActivity::class.java
                ).apply {
                    putExtra(EXTRA_SOURCE_ID, sourceName)
                    putExtra(EXTRA_TITLE, title)
                })
        }
    }
}

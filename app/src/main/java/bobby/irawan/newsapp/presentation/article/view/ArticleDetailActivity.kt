package bobby.irawan.newsapp.presentation.article.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import bobby.irawan.newsapp.AppController
import bobby.irawan.newsapp.databinding.ActivityArticleDetailBinding
import bobby.irawan.newsapp.di.viewmodel.ViewModelProviderFactory
import bobby.irawan.newsapp.presentation.article.viewmodel.ArticleDetailViewModel
import bobby.irawan.newsapp.utils.Constants.EXTRA_ARTICLE_URL
import bobby.irawan.newsapp.utils.Constants.EXTRA_TITLE
import bobby.irawan.newsapp.utils.orEmpty
import javax.inject.Inject

class ArticleDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleDetailBinding

    lateinit var articleDetailViewModel: ArticleDetailViewModel

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    override fun onCreate(savedInstanceState: Bundle?) {

        val articleComponent =
            (application as AppController).appComponent.articleComponent().create()
        articleComponent.inject(
            this
        )

        super.onCreate(savedInstanceState)
        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        articleDetailViewModel =
            ViewModelProvider(
                this, providerFactory
            ).get(ArticleDetailViewModel::class.java)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        articleDetailViewModel.getBundle(intent)
        observeViewModel()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun observeViewModel() {
        articleDetailViewModel.articleUrlLiveData.observe(this, Observer {
            initWebView()
            initWebViewUrl(it)
        })
        articleDetailViewModel.titleLiveData.observe(this, Observer {
            supportActionBar?.title = it
        })
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        val webSettings = binding.webViewArticle.settings
        webSettings.javaScriptEnabled = true
        binding.webViewArticle.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(webView: WebView?, url: String?) = false
        }
    }

    private fun initWebViewUrl(url: String?) {
        binding.webViewArticle.loadUrl(url?.orEmpty())
    }

    companion object {
        fun startActivity(activity: Activity, articleUrl: String, title: String) {
            activity.startActivity(
                Intent(
                    activity, ArticleDetailActivity::class.java
                ).apply {
                    putExtra(EXTRA_ARTICLE_URL, articleUrl)
                    putExtra(EXTRA_TITLE, title)
                })
        }
    }
}
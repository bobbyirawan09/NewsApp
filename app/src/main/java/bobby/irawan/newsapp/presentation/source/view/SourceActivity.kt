package bobby.irawan.newsapp.presentation.source.view

import android.R
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import bobby.irawan.newsapp.AppController
import bobby.irawan.newsapp.databinding.ActivitySourceBinding
import bobby.irawan.newsapp.di.viewmodel.ViewModelProviderFactory
import bobby.irawan.newsapp.presentation.article.view.ArticleActivity
import bobby.irawan.newsapp.presentation.model.SourceModelView
import bobby.irawan.newsapp.presentation.source.adapter.SourceAdapter
import bobby.irawan.newsapp.presentation.source.viewmodel.SourceViewModel
import bobby.irawan.newsapp.utils.Constants.EXTRA_CATEGORY_NAME
import bobby.irawan.newsapp.utils.isInternetConnected
import bobby.irawan.newsapp.utils.setGone
import bobby.irawan.newsapp.utils.setVisible
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import javax.inject.Inject

class SourceActivity : AppCompatActivity(), SourceAdapter.ClickListener {
    private lateinit var binding: ActivitySourceBinding
    private lateinit var sourceViewModel: SourceViewModel

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private val adapter by lazy {
        SourceAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        val sourceComponent = (application as AppController).appComponent.sourceComponent().create()
        sourceComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivitySourceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sourceViewModel = ViewModelProvider(this, providerFactory).get(SourceViewModel::class.java)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        onInitUi()
        onSetLoadingState()
        sourceViewModel.getBundle(intent)
            sourceViewModel.getSourceData(applicationContext.isInternetConnected())
        observerViewModel()
    }

    private fun onInitUi() {
        adapter.setClickListener(this)
        binding.recyclerViewSource.setHasFixedSize(true)
        binding.recyclerViewSource.adapter = adapter
        binding.textInputLayoutSearchSource.endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
        binding.textInputLayoutSearchSource.editText?.doAfterTextChanged { s ->
            sourceViewModel.onFilterSourceData(s.toString())
        }
    }

    private fun onSetLoadingState() {
        binding.recyclerViewSource.setGone()
        binding.progressBar.setVisible()
        binding.textInputLayoutSearchSource.isEnabled = false
    }

    override fun onNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun observerViewModel() {
        sourceViewModel.sourcesLiveData.observe(this, Observer {
            onSetResultState(it)
        })
        sourceViewModel.messageLiveData.observe(this, Observer {
            onSetErrorState(it)
        })
        sourceViewModel.filterSourcesLiveData.observe(this, Observer {
            onSetResultState(it)
        })
        sourceViewModel.titleLiveData.observe(this, Observer {
            supportActionBar?.title = it
        })
    }

    private fun onSetResultState(sources: List<SourceModelView>) {
        adapter.setSources(sources)
        binding.recyclerViewSource.setVisible()
        binding.progressBar.setGone()
        binding.textInputLayoutSearchSource.isEnabled = true
    }

    private fun onSetErrorState(message: String?) {
        binding.recyclerViewSource.setVisible()
        binding.progressBar.setGone()
        binding.textInputLayoutSearchSource.isEnabled = true
        Snackbar.make(binding.root, message.orEmpty(), Snackbar.LENGTH_SHORT)
            .show()
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

    override fun onClickListener(source: SourceModelView) {
        ArticleActivity.startActivity(
            this,
            sourceName = source.id.orEmpty(),
            title = source.name.orEmpty()
        )
    }

    companion object {
        fun startActivity(activity: Activity, categoryName: String) {
            activity.startActivity(Intent(
                activity, SourceActivity::class.java
            ).apply {
                putExtra(EXTRA_CATEGORY_NAME, categoryName)
            })
        }
    }
}

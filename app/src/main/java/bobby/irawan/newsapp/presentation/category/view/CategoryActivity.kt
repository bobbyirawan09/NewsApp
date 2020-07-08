package bobby.irawan.newsapp.presentation.category.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import bobby.irawan.newsapp.AppController
import bobby.irawan.newsapp.databinding.ActivityCategoryBinding
import bobby.irawan.newsapp.di.viewmodel.ViewModelProviderFactory
import bobby.irawan.newsapp.presentation.category.adapter.CategoryAdapter
import bobby.irawan.newsapp.presentation.category.viewmodel.CategoryViewModel
import bobby.irawan.newsapp.presentation.model.CategoryModelView
import bobby.irawan.newsapp.presentation.source.view.SourceActivity
import bobby.irawan.newsapp.utils.orEmpty
import javax.inject.Inject

class CategoryActivity : AppCompatActivity(),
    CategoryAdapter.ClickListener {

    private lateinit var binding: ActivityCategoryBinding
    lateinit var categoryViewModel: CategoryViewModel

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private val adapter: CategoryAdapter by lazy {
        CategoryAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        val categoryComponent = (application as AppController).appComponent.categoryComponent().create()
        categoryComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryViewModel = ViewModelProvider(this, providerFactory).get(CategoryViewModel::class.java)

        categoryViewModel.getCategoryData()
        observerViewModel()
    }

    private fun observerViewModel() {
        categoryViewModel.categoriesLiveData.observe(this, Observer {
            initRecyclerView(it)
        })
    }

    private fun initRecyclerView(categories: List<CategoryModelView>) {
        adapter.setCategories(categories)
        adapter.setClickListener(this)
        binding.recyclerViewCategory.setHasFixedSize(true)
        binding.recyclerViewCategory.adapter = adapter
    }

    override fun onClickListener(category: CategoryModelView) {
        SourceActivity.startActivity(
            this,
            categoryName = category.name.orEmpty()
        )
    }
}

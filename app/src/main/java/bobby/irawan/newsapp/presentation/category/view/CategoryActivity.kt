package bobby.irawan.newsapp.presentation.category.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import bobby.irawan.newsapp.databinding.ActivityCategoryBinding
import bobby.irawan.newsapp.presentation.category.adapter.CategoryAdapter
import bobby.irawan.newsapp.presentation.category.viewmodel.CategoryViewModel
import bobby.irawan.newsapp.presentation.model.CategoryModelView
import bobby.irawan.newsapp.presentation.source.view.SourceActivity
import bobby.irawan.newsapp.utils.orEmpty
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryActivity : AppCompatActivity(),
    CategoryAdapter.ClickListener {

    private lateinit var binding: ActivityCategoryBinding
    private val categoryViewModel by viewModel<CategoryViewModel>()
    private val adapter: CategoryAdapter by lazy {
        CategoryAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

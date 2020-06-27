package bobby.irawan.newsapp.di

import bobby.irawan.newsapp.presentation.article.viewmodel.ArticleDetailViewModel
import bobby.irawan.newsapp.presentation.article.viewmodel.ArticleViewModel
import bobby.irawan.newsapp.presentation.category.viewmodel.CategoryViewModel
import bobby.irawan.newsapp.presentation.source.viewmodel.SourceViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by bobbyirawan09 on 27/06/20.
 */

val viewModelModule = module {
    viewModel {
        CategoryViewModel(
            get()
        )
    }

    viewModel {
        ArticleViewModel(
            get()
        )
    }

    viewModel {
        SourceViewModel(
            get()
        )
    }

    viewModel {
        ArticleDetailViewModel()
    }
}
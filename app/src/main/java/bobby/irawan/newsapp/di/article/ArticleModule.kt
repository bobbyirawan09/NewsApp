package bobby.irawan.newsapp.di.article

import androidx.lifecycle.ViewModel
import bobby.irawan.newsapp.data.articles.service.ArticleApi
import bobby.irawan.newsapp.data.articles.service.ArticleService
import bobby.irawan.newsapp.data.articles.service.ArticleServiceImpl
import bobby.irawan.newsapp.di.viewmodel.ViewModelKey
import bobby.irawan.newsapp.domain.article.ArticleRepository
import bobby.irawan.newsapp.domain.article.ArticleRepositoryImpl
import bobby.irawan.newsapp.presentation.article.viewmodel.ArticleDetailViewModel
import bobby.irawan.newsapp.presentation.article.viewmodel.ArticleViewModel
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

/**
 * Created by bobbyirawan09 on 07/07/20.
 */
@Module
class ArticleModule {

    @ArticleScope
    @Provides
    fun provideArticleRepository(articleService: ArticleService): ArticleRepository {
        return ArticleRepositoryImpl(articleService)
    }

    @ArticleScope
    @Provides
    fun providesArticleService(articleApi: ArticleApi): ArticleService {
        return ArticleServiceImpl(articleApi)
    }

    @ArticleScope
    @Provides
    fun providesArticlesApi(retrofit: Retrofit): ArticleApi {
        return retrofit.create(ArticleApi::class.java)
    }

    @Provides
    @IntoMap
    @ViewModelKey(ArticleViewModel::class)
    fun provideArticleViewModel(viewModel: ArticleViewModel): ViewModel {
        return viewModel
    }

    @Provides
    @IntoMap
    @ViewModelKey(ArticleDetailViewModel::class)
    fun provideArticleDetailViewModel(viewModel: ArticleDetailViewModel): ViewModel {
        return viewModel
    }

}
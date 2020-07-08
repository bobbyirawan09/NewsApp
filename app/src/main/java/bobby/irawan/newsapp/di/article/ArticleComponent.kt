package bobby.irawan.newsapp.di.article

import bobby.irawan.newsapp.presentation.article.view.ArticleActivity
import bobby.irawan.newsapp.presentation.article.view.ArticleDetailActivity
import dagger.Subcomponent

/**
 * Created by bobbyirawan09 on 07/07/20.
 */
@ArticleScope
@Subcomponent(modules = [ArticleModule::class])
interface ArticleComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ArticleComponent
    }

    fun inject(activity: ArticleActivity)
    fun inject(activity: ArticleDetailActivity)

}
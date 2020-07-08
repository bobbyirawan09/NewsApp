package bobby.irawan.newsapp.di

import android.content.Context
import bobby.irawan.newsapp.di.article.ArticleComponent
import bobby.irawan.newsapp.di.category.CategoryComponent
import bobby.irawan.newsapp.di.source.SourceComponent
import bobby.irawan.newsapp.di.viewmodel.ViewModelFactoryModule
import bobby.irawan.newsapp.presentation.article.view.ArticleActivity
import bobby.irawan.newsapp.presentation.article.view.ArticleDetailActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by bobbyirawan09 on 07/07/20.
 */
@Singleton
@Component(modules = [AppSubcomponent::class, ConnectionModule::class, ViewModelFactoryModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun categoryComponent(): CategoryComponent.Factory
    fun sourceComponent(): SourceComponent.Factory
    fun articleComponent(): ArticleComponent.Factory

}
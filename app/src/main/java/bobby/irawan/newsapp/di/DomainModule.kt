package bobby.irawan.newsapp.di

import bobby.irawan.newsapp.domain.article.ArticleRepository
import bobby.irawan.newsapp.domain.article.ArticleRepositoryImpl
import bobby.irawan.newsapp.domain.category.CategoryRepository
import bobby.irawan.newsapp.domain.category.CategoryRepositoryImpl
import bobby.irawan.newsapp.domain.source.SourceRepository
import bobby.irawan.newsapp.domain.source.SourceRepositoryImpl
import org.koin.dsl.module

/**
 * Created by bobbyirawan09 on 27/06/20.
 */

val domainModule = module {
    single<ArticleRepository> {
        ArticleRepositoryImpl(get())
    }

    single<SourceRepository> {
        SourceRepositoryImpl(get())
    }

    single<CategoryRepository> {
        CategoryRepositoryImpl(get())
    }
}
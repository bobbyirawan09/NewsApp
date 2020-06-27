package bobby.irawan.newsapp.di

import androidx.room.Room
import bobby.irawan.newsapp.AppDatabase
import bobby.irawan.newsapp.data.articles.service.ArticleService
import bobby.irawan.newsapp.data.articles.service.ArticleServiceImpl
import bobby.irawan.newsapp.data.category.service.CategoryService
import bobby.irawan.newsapp.data.category.service.CategoryServiceImpl
import bobby.irawan.newsapp.data.sources.service.SourceService
import bobby.irawan.newsapp.data.sources.service.SourceServiceImpl
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Created by bobbyirawan09 on 27/06/20.
 */

val dataModule = module {
    single<CategoryService> {
        CategoryServiceImpl(get())
    }

    single<ArticleService> {
        ArticleServiceImpl(get(), get())
    }

    single<SourceService> {
        SourceServiceImpl(get(), get())
    }

    single {
        Gson()
    }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "category")
            .createFromAsset("news_category.db").build()
    }

    single {
        get<AppDatabase>().categoryDao()
    }
}
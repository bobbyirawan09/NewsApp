package bobby.irawan.newsapp.di

import bobby.irawan.newsapp.data.articles.service.ArticleApi
import bobby.irawan.newsapp.data.sources.service.SourcesApi
import bobby.irawan.newsapp.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by bobbyirawan09 on 26/06/20.
 */

val retrofitModule = module {
    fun provideHttpClient() =
        OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .connectTimeout(30, TimeUnit.SECONDS)
            .callTimeout(30, TimeUnit.SECONDS)
            .build()

    fun provideRetrofit(httpClient: OkHttpClient) =
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

    single {
        provideHttpClient()
    }

    single {
        provideRetrofit(get())
    }

    single { get<Retrofit>().create(SourcesApi::class.java) }

    single { get<Retrofit>().create(ArticleApi::class.java) }
}
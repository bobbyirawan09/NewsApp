package bobby.irawan.newsapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import bobby.irawan.newsapp.AppDatabase
import bobby.irawan.newsapp.data.category.service.CategoryDao
import bobby.irawan.newsapp.utils.Constants
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by bobbyirawan09 on 07/07/20.
 */
@Module
class ConnectionModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(context: Context): RoomDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "category")
            .createFromAsset("news_category.db").build()
    }

    @Singleton
    @Provides
    fun provideRoomDao(database: RoomDatabase): CategoryDao {
        return (database as AppDatabase).categoryDao()
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .connectTimeout(30, TimeUnit.SECONDS)
            .callTimeout(30, TimeUnit.SECONDS)
            .build()
    }
}
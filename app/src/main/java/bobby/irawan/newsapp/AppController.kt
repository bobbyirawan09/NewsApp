package bobby.irawan.newsapp

import android.app.Application
import bobby.irawan.newsapp.di.dataModule
import bobby.irawan.newsapp.di.domainModule
import bobby.irawan.newsapp.di.retrofitModule
import bobby.irawan.newsapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by bobbyirawan09 on 26/06/20.
 */

class AppController : Application() {

    override fun onCreate() {
        super.onCreate()

        instance = this
        startKoin {
            androidContext(this@AppController)
            modules(retrofitModule, dataModule, viewModelModule, domainModule)
        }
    }

    companion object {
        private lateinit var instance: AppController
        fun getInstance(): AppController = instance
    }
}
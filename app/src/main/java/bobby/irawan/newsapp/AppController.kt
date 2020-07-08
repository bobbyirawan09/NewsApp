package bobby.irawan.newsapp

import android.app.AppComponentFactory
import android.app.Application
import bobby.irawan.newsapp.di.AppComponent
import bobby.irawan.newsapp.di.DaggerAppComponent

/**
 * Created by bobbyirawan09 on 26/06/20.
 */

class AppController : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
    }

    companion object {
        private lateinit var instance: AppController
        fun getInstance(): AppController = instance
    }
}
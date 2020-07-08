package bobby.irawan.newsapp.di.viewmodel

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by bobbyirawan09 on 07/07/20.
 */
@Module
class ViewModelFactoryModule {

    @Provides
    @Singleton
    fun provideViewModelFactory(viewModelProviderFactory: ViewModelProviderFactory): ViewModelProvider.Factory {
        return viewModelProviderFactory
    }
}
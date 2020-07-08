package bobby.irawan.newsapp.di.source

import androidx.lifecycle.ViewModel
import bobby.irawan.newsapp.data.sources.service.SourceService
import bobby.irawan.newsapp.data.sources.service.SourceServiceImpl
import bobby.irawan.newsapp.data.sources.service.SourcesApi
import bobby.irawan.newsapp.di.viewmodel.ViewModelKey
import bobby.irawan.newsapp.domain.source.SourceRepository
import bobby.irawan.newsapp.domain.source.SourceRepositoryImpl
import bobby.irawan.newsapp.presentation.source.viewmodel.SourceViewModel
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by bobbyirawan09 on 07/07/20.
 */
@Module
class SourceModule {

    @SourceScope
    @Provides
    fun providesSourceService(sourceApi: SourcesApi): SourceService {
        return SourceServiceImpl(sourceApi)
    }

    @SourceScope
    @Provides
    fun provideSourceRepository(sourceService: SourceService) : SourceRepository {
        return SourceRepositoryImpl(sourceService)
    }

    @SourceScope
    @Provides
    fun providesSourcesApi(retrofit: Retrofit): SourcesApi {
        return retrofit.create(SourcesApi::class.java)
    }

    @Provides
    @IntoMap
    @ViewModelKey(SourceViewModel::class)
    fun provideSourceViewModel(viewModel: SourceViewModel): ViewModel {
        return viewModel
    }
}
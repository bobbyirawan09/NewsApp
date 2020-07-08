package bobby.irawan.newsapp.di.source

import bobby.irawan.newsapp.di.category.CategoryComponent
import bobby.irawan.newsapp.di.category.CategoryModule
import bobby.irawan.newsapp.di.category.CategoryScope
import bobby.irawan.newsapp.presentation.category.view.CategoryActivity
import bobby.irawan.newsapp.presentation.source.view.SourceActivity
import dagger.Subcomponent

/**
 * Created by bobbyirawan09 on 07/07/20.
 */
@SourceScope
@Subcomponent(modules = [SourceModule::class])
interface SourceComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): SourceComponent
    }

    fun inject(activity: SourceActivity)

}
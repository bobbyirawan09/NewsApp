package bobby.irawan.newsapp.di.category

import bobby.irawan.newsapp.presentation.category.view.CategoryActivity
import dagger.Subcomponent

/**
 * Created by bobbyirawan09 on 07/07/20.
 */
@CategoryScope
@Subcomponent(modules = [CategoryModule::class])
interface CategoryComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): CategoryComponent
    }

    fun inject(activity: CategoryActivity)

}
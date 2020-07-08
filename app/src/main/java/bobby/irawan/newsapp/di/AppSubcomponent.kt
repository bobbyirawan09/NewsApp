package bobby.irawan.newsapp.di

import bobby.irawan.newsapp.di.article.ArticleComponent
import bobby.irawan.newsapp.di.category.CategoryComponent
import bobby.irawan.newsapp.di.source.SourceComponent
import dagger.Module

/**
 * Created by bobbyirawan09 on 07/07/20.
 */
@Module(subcomponents = [CategoryComponent::class, SourceComponent::class, ArticleComponent::class])
class AppSubcomponent {
}
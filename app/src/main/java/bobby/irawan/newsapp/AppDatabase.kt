package bobby.irawan.newsapp

import androidx.room.Database
import androidx.room.RoomDatabase
import bobby.irawan.newsapp.data.category.model.CategoryEntity
import bobby.irawan.newsapp.data.category.service.CategoryDao
import javax.inject.Singleton

/**
 * Created by bobbyirawan09 on 27/06/20.
 */
@Database(entities = [CategoryEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
}
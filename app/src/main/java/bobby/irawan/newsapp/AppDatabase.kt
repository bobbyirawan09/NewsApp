package bobby.irawan.newsapp

import androidx.room.Database
import androidx.room.RoomDatabase
import bobby.irawan.newsapp.data.category.model.CategoryEntity
import bobby.irawan.newsapp.data.category.service.CategoryDao

/**
 * Created by bobbyirawan09 on 27/06/20.
 */
@Database(entities = [CategoryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
}
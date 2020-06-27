package bobby.irawan.newsapp.data.category.service

import androidx.room.Dao
import androidx.room.Query
import bobby.irawan.newsapp.data.category.model.CategoryEntity

/**
 * Created by bobbyirawan09 on 26/06/20.
 */
@Dao
interface CategoryDao {
    @Query("SELECT * from category ORDER BY id ASC")
    suspend fun getCategories(): List<CategoryEntity>
}
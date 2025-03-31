package com.avastronomia.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.avastronomia.datasource.dao.RecipeDao
import com.avastronomia.datasource.local.entity.RecipeEntity
import com.avastronomia.datasource.utils.ListConverter

@Database(entities = [RecipeEntity::class], version = 1, exportSchema = false)
@TypeConverters(ListConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getRecipeDao(): RecipeDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
             return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                TableConstants.APP_DATABASE_NAME
            ).fallbackToDestructiveMigration()
                .build()
        }

        fun destroyDatabase() {
            instance = null
        }
    }
}

object TableConstants {
    const val APP_DATABASE_NAME = "recipe_catalog_app_database"
    const val RECIPE_TABLE_NAME = "recipe"
}
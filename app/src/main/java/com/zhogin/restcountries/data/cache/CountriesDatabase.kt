package com.zhogin.restcountries.data.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zhogin.restcountries.data.cache.dao.CountryDao
import com.zhogin.restcountries.data.cache.entity.CountryEntity


class CountriesDatabase internal constructor(private val database: CountriesRoomDatabase) {
    val countryDao: CountryDao
        get() = database.countryDao()
}

@Database(
    entities = [
        CountryEntity::class
    ],
    version = 1,
    exportSchema = true
)
internal abstract class CountriesRoomDatabase : RoomDatabase() {
    abstract fun countryDao() : CountryDao
}
fun CountriesDatabase(applicationContext: Context) : CountriesDatabase {
    val productRoomDatabase = Room.databaseBuilder(
        checkNotNull(applicationContext.applicationContext),
        CountriesRoomDatabase::class.java,
        "public_tasks_db"
    )
        .build()
    return CountriesDatabase(productRoomDatabase)
}
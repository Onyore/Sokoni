package com.example.sokoni.Storage.repository

import android.content.Context
import com.example.sokoni.Storage.repository.daos.CartDao
import com.example.sokoni.Storage.repository.daos.ProfileDao
import com.example.sokoni.Storage.repository.daos.TagDao
import com.example.sokoni.models.oauth.Profile
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sokoni.models.oauth.Cart.Cart
import com.example.sokoni.models.oauth.Main.Tag
import com.example.sokoni.models.oauth.Profile


@Database( entities = arrayOf(Profile::class, Cart::class, Tag::class), version = 1, exportSchema = false)
abstract class SokoniDatabase : RoomDatabase() {
    companion object {
        private lateinit var INSTANCE: SokoniDatabase
        fun getDatabase(context: Context): SokoniDatabase? {
            synchronized(SokoniDatabase::class.java) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                        SokoniDatabase::class.java, "sokoni_database")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
            }
            return INSTANCE
        }
    }

    abstract fun profileDao(): ProfileDao
    abstract fun cartDao(): CartDao
    abstract fun tagDao(): TagDao
}


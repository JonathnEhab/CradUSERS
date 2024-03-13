package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.pojo.UserInfoEntity

@Database(entities = [UserInfoEntity::class], version = 1, exportSchema = false)
abstract class UserInfoDatabase : RoomDatabase() {
    abstract fun userInfoDao(): UserInfoDao

    companion object {
        @Volatile
        private var INSTANCE: UserInfoDatabase? = null

        fun getInstance(context: Context): UserInfoDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserInfoDatabase::class.java,
                    "users_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.pojo.UserInfoEntity

@Dao
interface UserInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserInfo(userInfo: UserInfoEntity)

    @Query("SELECT * FROM user_info")
    suspend fun getAllUserInfo(): List<UserInfoEntity>

    @Query("SELECT * FROM user_info WHERE id = :userId")
    suspend fun getUserInfoById(userId: String): UserInfoEntity?

    @Update
    suspend fun updateUserInfo(userInfo: UserInfoEntity)

    @Query("DELETE FROM user_info WHERE id = :userId")
    suspend fun deleteUserInfoById(userId: String)
}

package com.example.data.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_info")
data class UserInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name:String,
    val age:String,
    val email:String,
    val phone:String
)
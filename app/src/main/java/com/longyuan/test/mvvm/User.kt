package com.longyuan.test.mvvm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = AppDatabase.DATABASE_NAME)
data class User(@PrimaryKey
                @ColumnInfo(name = "username") val username: String,
                @ColumnInfo(name = "password") val password: String,
                @ColumnInfo(name = "address") val address: String,
                @ColumnInfo(name = "phone_number") val phoneNumber: String)
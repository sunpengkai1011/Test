package com.longyuan.test.mvvm

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM TEST_DATABASE")
    fun getUsers(): LiveData<List<User>>

    @Query("SELECT * FROM TEST_DATABASE WHERE USERNAME = (:username)")
    fun getUserByName(username: String): LiveData<User>

    @Insert
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)
}

package com.longyuan.test.mvvm

import android.content.Context
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserRepository(context: Context) {

    private var userDao: UserDao?

    companion object{
        @Volatile private var instance: UserRepository? = null

        fun getInstance(context: Context) = instance ?: synchronized(this){
            instance ?: UserRepository(context).also { instance = it }
        }
    }

    init {
        val appDatabase = AppDatabase.getDatabase(context)
        userDao = appDatabase?.userDao()
    }

    fun getUsers() = userDao?.getUsers()

    fun getUserByName(username: String) = userDao?.getUserByName(username)

    fun addUser(user: User) : Single<User>{
        return Single.create<User> {
            userDao?.insertUser(user)
            it.onSuccess(user)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteUser(user: User): Single<User>{
        return Single.create<User> {
            userDao?.deleteUser(user)
            it.onSuccess(user)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
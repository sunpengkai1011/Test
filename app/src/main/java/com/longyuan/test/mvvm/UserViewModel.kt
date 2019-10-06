package com.longyuan.test.mvvm

import android.content.Context
import androidx.lifecycle.ViewModel

class UserViewModel(context: Context): ViewModel() {

    private val userRepository = UserRepository.getInstance(context)

    val users = userRepository.getUsers()

    fun addUser(user: User) = userRepository.addUser(user)

    fun deleteUser(user: User) = userRepository.deleteUser(user)
}
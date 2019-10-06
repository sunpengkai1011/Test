package com.longyuan.test.mvvm

import android.content.Context

object InjectorUtils {

    fun provideUserViewModelFactory(context: Context): UserViewModelFactory{
        return UserViewModelFactory(context)
    }
}
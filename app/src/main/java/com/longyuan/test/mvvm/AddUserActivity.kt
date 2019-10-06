package com.longyuan.test.mvvm

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.longyuan.test.R
import kotlinx.android.synthetic.main.activity_add_user.*

class AddUserActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        val userViewModel = UserViewModelFactory(applicationContext).create(UserViewModel::class.java)

        btnAdd.setOnClickListener{
            userViewModel.addUser(User(etUsername.text.toString(),
                etPassword.text.toString(),
                etAddress.text.toString(),
                etPhoneNumber.text.toString())).subscribe({
                Toast.makeText(this, "add ${it.username} succeed", Toast.LENGTH_SHORT).show()
                this.finish()
            }, {
                Toast.makeText(this, "add user failed", Toast.LENGTH_SHORT).show()
            })

        }
    }
}
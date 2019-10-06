package com.longyuan.test.mvvm

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.longyuan.test.R
import kotlinx.android.synthetic.main.activity_users.*

class UserListActivty: AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        actionBar?.title = "Users"

        val userViewModelFactory = InjectorUtils.provideUserViewModelFactory(this)
        userViewModel = ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel::class.java)
        val usersAdapter = UsersAdapter()
        rcvUsers.apply {
            adapter = usersAdapter
            layoutManager = LinearLayoutManager(this@UserListActivty)
        }

        usersAdapter.onItemClickListener = { user ->
            AlertDialog.Builder(this).setMessage("Do you want to delete ${user.username}?")
                .setPositiveButton("Ok") { _, _ ->
                    userViewModel.deleteUser(user).subscribe({
                        Toast.makeText(this, "delete ${it.username} succeed", Toast.LENGTH_SHORT).show()
                    },{
                        Toast.makeText(this, "delete failed", Toast.LENGTH_SHORT).show()
                    })
                }.setNegativeButton("Cancel", null)
                .create().show()
        }

        userViewModel.users?.observe(this, Observer<List<User>>{
            if (it.isNullOrEmpty()){
                tvNoData.visibility = View.VISIBLE
                rcvUsers.visibility = View.GONE
            }else{
                tvNoData.visibility = View.GONE
                rcvUsers.visibility = View.VISIBLE
                usersAdapter.setUsers(it)
            }
        })

        fabAdd.setOnClickListener{
            startActivity(Intent(this, AddUserActivity::class.java))
        }
    }
}
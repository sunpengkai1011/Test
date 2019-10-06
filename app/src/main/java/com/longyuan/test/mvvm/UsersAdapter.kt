package com.longyuan.test.mvvm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.longyuan.test.R
import kotlinx.android.synthetic.main.item_user.view.*

class UsersAdapter: RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    private var users: List<User> = arrayListOf()
    var onItemClickListener: ((User) -> Unit)? = null

    fun setUsers(data: List<User>){
        users = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]

        holder.itemView.apply {
            tvUsername.text = user.username
            tvAddress.text = user.address
            tvPhoneNumber.text = user.phoneNumber
            setOnLongClickListener {
                onItemClickListener?.invoke(user)
                true
            }
        }
    }


    class UserViewHolder(view: View): RecyclerView.ViewHolder(view)
}
package com.example.recuclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

interface UserActionListener {
    fun onUserMove(user: Users, moveBy: Int)
    fun onUserDelete(user: Users)
    fun onUserAdd(user: Users)
    fun onUserDetails(user: Users)
    }

class UsersAdapter (private val usersList:ArrayList<Users>) : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>(){

    var onItemClick : ((Users) -> Unit)? = null

    class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val imageView : ImageView = itemView.findViewById(R.id.imageView)
        val largeTextView: TextView = itemView.findViewById(R.id.userNameTextView)
        val smallTextView: TextView = itemView.findViewById(R.id.userCompanyTextView)
        val userImage:ImageView=itemView.findViewById(R.id.photoImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view = LayoutInflater.from(parent.context) .inflate(R.layout.item_user , parent, false)
            return UsersViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user=usersList[position]
        holder.largeTextView.text = user.name
        holder.smallTextView.text = user.department
        holder.userImage.imageAlpha=R.drawable.ic_user_avatar

        holder.itemView.setOnClickListener {
        onItemClick?.invoke(user) }
    }

    override fun getItemCount(): Int {
        return usersList.size
    }
}
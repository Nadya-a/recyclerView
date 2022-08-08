package com.example.recuclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

/* interface UserActionListener {
    fun onUserMove(user: Users, moveBy: Int)
    fun onUserDelete(user: Users)
    fun onUserDetails(user: Users)
} */

class UsersAdapter (private val usersList:MutableList<Users>) : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    var onItemClick: ((Users) -> Unit)? = null
    var onItemLongClick: ((Users) -> Unit)? = null
    var deleteClick: ((Users) -> Unit)? = null

    class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val largeTextView: TextView = itemView.findViewById(R.id.userNameTextView)
        val smallTextView: TextView = itemView.findViewById(R.id.userCompanyTextView)

        val deleteImage: ImageView =itemView.findViewById(R.id.deleteImageViewButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UsersViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = usersList[position]
        holder.largeTextView.text = user.name
        holder.smallTextView.text = user.department
        if(holder.itemView.isSelected)
            holder.deleteImage.visibility=View.GONE

        holder.deleteImage.setOnClickListener { deleteClick?.invoke(user) }

        holder.itemView.setOnClickListener { onItemClick?.invoke(user) }

        holder.itemView.setOnLongClickListener {
            //onItemLongClick?.invoke(user)
            if(holder.deleteImage.isVisible)
                holder.deleteImage.visibility=View.GONE
            else holder.deleteImage.visibility=View.VISIBLE
            true
        }
    }

    override fun getItemCount(): Int {
            return usersList.size
        }
}
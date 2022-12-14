package com.example.recuclerview

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recuclerview.model.Users
import com.example.recuclerview.model.UsersListener

class UsersAdapter (
    ) : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>()
{

    var onItemClick: ((Users) -> Unit)? = null
    //var onItemLongClick: ((Users) -> Unit)? = null
    var deleteClick: ((Users) -> Unit)? = null

    var usersList = mutableListOf<Users>()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val largeTextView: TextView = itemView.findViewById(R.id.userNameTextView)
        val smallTextView: TextView = itemView.findViewById(R.id.userCompanyTextView)

        val moreImage: ImageView =itemView.findViewById(R.id.moreImageViewButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UsersViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = usersList[position]
        holder.largeTextView.text = user.name
        holder.smallTextView.text = user.department

       holder.moreImage.setOnClickListener { showPopupMenu(holder.moreImage, user)}

        holder.itemView.setOnClickListener { onItemClick?.invoke(user) }

        holder.itemView.setOnLongClickListener {
            deleteClick?.invoke(user)
            true
        }
    }

    override fun getItemCount(): Int = usersList.size

    private fun showPopupMenu(view: View, user : Users) {
        val popupMenu = PopupMenu(view.context, view)

        popupMenu.menu.add(0, ID_MORE, Menu.NONE, "More")
        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, "Remove")

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_MORE -> {
                    onItemClick?.invoke(user)
                }
                ID_REMOVE -> {
                    deleteClick?.invoke(user)
                }
            }
            return@setOnMenuItemClickListener true
        }

        popupMenu.show()
    }
    companion object {
        private const val ID_MORE = 1
        private const val ID_REMOVE = 2
    }

    fun sortBeginning (){
        usersList.sortBy { it.name.lowercase() }
        notifyDataSetChanged()
    }

    fun sortLast(){
        usersList.sortBy{ it.name.length}
        //usersList.sortWith(compareBy{it.name.length})
        notifyDataSetChanged()
    }

    fun cancel(){
        usersList.sortBy { it.id }
        notifyDataSetChanged()
    }

}

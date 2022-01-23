package com.example.trainee.ui.screens.screen_section_department

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.trainee.R
import com.example.trainee.data.model.User

interface UserListAdapterClicks {
    fun onItemClick(model: User)
}

class TestAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listUsers: List<User> = mutableListOf()
    private var userListAdapterClicks: UserListAdapterClicks? = null

    inner class DefaultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar: ImageView = itemView.findViewById(R.id.avatarImageView)
        val nameUserTextView: TextView = itemView.findViewById(R.id.nameUserTextView)
        val positionUserTextView: TextView = itemView.findViewById(R.id.positionUserTextView)
        val tagUserTextView: TextView = itemView.findViewById(R.id.tagUserTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DefaultViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as TestAdapter.DefaultViewHolder
        viewHolder.nameUserTextView.text =
            listUsers[position].firstName + " " + listUsers[position].lastName
        viewHolder.positionUserTextView.text = listUsers[position].position
        viewHolder.tagUserTextView.text = listUsers[position].userTag.toLowerCase()
        Glide
            .with(viewHolder.itemView)
            .asBitmap()
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .load(listUsers[position].avatar)
            .placeholder(R.drawable.ic_face_90)
            .into(viewHolder.avatar)

        viewHolder.itemView.setOnClickListener {
            userListAdapterClicks?.onItemClick(listUsers[position])
        }
    }

    override fun getItemCount(): Int {
        return listUsers.size
    }

    fun attachClicks(userListAdapterClicks: UserListAdapterClicks) {
        this.userListAdapterClicks = userListAdapterClicks
    }

    fun addData(list: List<User>) {
        //listUsers.clear()
        listUsers = list
        notifyDataSetChanged()
    }
}
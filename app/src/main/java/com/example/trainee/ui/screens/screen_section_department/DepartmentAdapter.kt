package com.example.trainee.ui.screens.screen_section_department

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.trainee.R
import com.example.trainee.data.model.User

interface UserListAdapterClicks {
    fun onItemClick(model: User)
}

class DepartmentAdapter : ListAdapter<User, RecyclerView.ViewHolder>(UserItemDiffCallback()) {
    private var userListAdapterClicks: UserListAdapterClicks? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as DepartmentAdapter.DefaultViewHolder
        viewHolder.nameUserTextView.text =
            currentList[position].firstName + " " + currentList[position].lastName
        viewHolder.positionUserTextView.text = currentList[position].department
        viewHolder.tagUserTextView.text = currentList[position].userTag.toLowerCase()
        Glide
            .with(viewHolder.itemView)
            .asBitmap()
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .load(currentList[position].avatar)
            .placeholder(R.drawable.ic_face_90)
            .into(viewHolder.avatar)

        viewHolder.itemView.setOnClickListener {
            userListAdapterClicks?.onItemClick(currentList[position])
        }
    }

    override fun onCurrentListChanged(
        previousList: MutableList<User>,
        currentList: MutableList<User>
    ) {
        super.onCurrentListChanged(previousList, currentList)
    }

    fun attachClicks(userListAdapterClicks: UserListAdapterClicks) {
        this.userListAdapterClicks = userListAdapterClicks
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DefaultViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        )
    }

    inner class DefaultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar: ImageView = itemView.findViewById(R.id.avatarImageView)
        val nameUserTextView: TextView = itemView.findViewById(R.id.nameUserTextView)
        val positionUserTextView: TextView = itemView.findViewById(R.id.positionUserTextView)
        val tagUserTextView: TextView = itemView.findViewById(R.id.tagUserTextView)
    }
}

class UserItemDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = oldItem == newItem
}
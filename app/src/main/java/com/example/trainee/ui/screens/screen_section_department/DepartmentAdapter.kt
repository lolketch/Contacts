package com.example.trainee.ui.screens.screen_section_department

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.trainee.R
import com.example.trainee.data.model.User
import com.example.trainee.databinding.ItemUserBinding

interface UserListAdapterClicks {
    fun onItemClick(model: User)
}

class DepartmentAdapter : ListAdapter<User, UserViewHolder>(UserItemDiffCallback()) {

    private var userListAdapterClicks: UserListAdapterClicks? = null

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        holder.bind(currentList[position])

        holder.itemView.setOnClickListener {
            userListAdapterClicks?.onItemClick(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return UserViewHolder(
            ItemUserBinding.inflate(layoutInflater, parent, false)
        )
    }

    fun attachClicks(userListAdapterClicks: UserListAdapterClicks) {
        this.userListAdapterClicks = userListAdapterClicks
    }
}

class UserViewHolder(
    private val binding: ItemUserBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User) {
        with(binding) {
            Glide
                .with(itemView)
                .asBitmap()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .load(user.avatar)
                .placeholder(R.drawable.ic_face_90)
                .into(avatarImageView)
            nameUserTextView.text = user.firstName.plus(" ").plus(user.lastName)
            positionUserTextView.text = user.department
            tagUserTextView.text = user.userTag.lowercase()
        }
    }
}

class UserItemDiffCallback : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }
}
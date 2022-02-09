package com.example.feature_list.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.api.UserDto
import com.example.feature_list.R
import com.example.feature_list.databinding.ItemUserBinding

interface UserListAdapterClicks {
    fun onItemClick(model: UserDto)
}

class DepartmentAdapter : ListAdapter<UserDto, UserViewHolder>(UserItemDiffCallback()) {

    private var userListAdapterClicks: UserListAdapterClicks? = null

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        holder.bind(currentList[position])

        holder.itemView.setOnClickListener {
            userListAdapterClicks?.onItemClick(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return UserViewHolder(ItemUserBinding.inflate(layoutInflater, parent, false))
    }

    fun attachClicks(userListAdapterClicks: UserListAdapterClicks) {
        this.userListAdapterClicks = userListAdapterClicks
    }
}

class UserViewHolder(
    private val binding: ItemUserBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(userDto: UserDto) {
        with(binding) {
            Glide
                .with(itemView)
                .asBitmap()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .load(userDto.avatar)
                .placeholder(R.drawable.ic_face_90)
                .into(avatarImageView)
            nameUserTextView.text = userDto.firstName.plus(" ").plus(userDto.lastName)
            positionUserTextView.text = userDto.position
            tagUserTextView.text = userDto.userTag.lowercase()
        }
    }
}

class UserItemDiffCallback : DiffUtil.ItemCallback<UserDto>() {

    override fun areItemsTheSame(oldItem: UserDto, newItem: UserDto): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: UserDto, newItem: UserDto): Boolean {
        return oldItem.id == newItem.id
    }
}
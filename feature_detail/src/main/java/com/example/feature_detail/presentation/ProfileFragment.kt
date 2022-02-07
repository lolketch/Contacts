package com.example.feature_detail.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.api.UserDto
import com.example.core.BaseFragment
import com.example.core.Constance.USER_DATA
import com.example.feature_detail.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = arguments?.getParcelable<UserDto>(USER_DATA)
        user?.let {
            initUi(it)
        }
    }

    private fun initUi(userDto: UserDto) {
        val fullName = "${userDto.firstName} ${userDto.lastName}"

        binding.firstName.text = fullName
        binding.tag.text = userDto.userTag
        binding.position.text = userDto.position
        binding.birthday.text = userDto.birthday.toString()
        Glide
            .with(this)
            .load(userDto.avatar)
            .into(binding.userPhoto)
    }

}
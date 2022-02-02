package com.example.feature_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.feature_detail.databinding.FragmentProfileBinding
import com.example.trainee.data.model.User

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val USER_DATA = "USER_DATA"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        val user = arguments?.get(USER_DATA) as User

        initUi(user)
    }

    private fun initUi(user: User) {
        val fullName = "${user.firstName} ${user.lastName}"

        binding.firstName.text = fullName
        binding.tag.text = user.userTag
        binding.position.text = user.position
        binding.birthday.text = user.birthday.toString()
        Glide
            .with(this)
            .load(user.avatar)
            .into(binding.userPhoto)
    }
}
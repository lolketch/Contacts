package com.example.trainee.ui.screens.screen_profile

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.trainee.R
import com.example.trainee.data.model.User

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    companion object {
        const val USER_DATA = "USER_DATA"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val test = arguments?.get(USER_DATA) as User

        val photo = view.findViewById<ImageView>(R.id.profileImageView)
        val name = view.findViewById<TextView>(R.id.firstNameTextView)
        val tagUser = view.findViewById<TextView>(R.id.tagUserProfileTextView)
        val position = view.findViewById<TextView>(R.id.positionUserProfileTextView)
        val birthday = view.findViewById<TextView>(R.id.birthdayTextView)
        val age = view.findViewById<TextView>(R.id.ageTextView)

        name.text = test.firstName + " " + test.lastName
        tagUser.text = test.userTag
        position.text = test.position
        birthday.text = test.birthday.toString()
        Glide
            .with(this)
            .asBitmap()
            .load(test.avatar)
            .into(photo)

    }
}
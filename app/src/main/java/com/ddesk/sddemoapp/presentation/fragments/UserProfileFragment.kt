package com.ddesk.sddemoapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.ddesk.sddemoapp.R
import com.ddesk.sddemoapp.data.model.UserEntity
import com.ddesk.sddemoapp.databinding.FragmentUserProfileBinding


class UserProfileFragment : Fragment() {

    private lateinit var fragmentUserProfileBinding: FragmentUserProfileBinding
    private lateinit var user: UserEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            user = bundle.getParcelable("USER_KEY") ?: throw IllegalStateException("User data not found!")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentUserProfileBinding = FragmentUserProfileBinding.bind(view)
        fragmentUserProfileBinding.apply {


            Glide.with(requireActivity())
                .load(user.picture)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(ivProfileIcon)

            tvNameAgeGender.text = "${user.fullName} ${user.age} ${user.gender}"
            tvAddress.text = "${user.street}, \n${user.city}, ${user.street}"
            tvRating.text = "${user.matchScore}"


            tvEducation.text = "${user.education}"
            tvReligion.text = "${user.religion}"

            tvContact.text = "${user.phone}"
            tvEmailAddress.text = "${user.email}"

        }
    }

}
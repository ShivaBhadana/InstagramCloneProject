package com.shivay.instagramcloneproject.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.shivay.instagramcloneproject.SignUpActivity
import com.shivay.instagramcloneproject.adapter.VIewPagerAdapter
import com.shivay.instagramcloneproject.databinding.FragmentProfileBinding
import com.shivay.instagramcloneproject.model.User
import com.shivay.instagramcloneproject.utils.USER_NODE
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private lateinit var binding:FragmentProfileBinding
    private lateinit var viewPagerAdapter: VIewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentProfileBinding.inflate(inflater, container, false)

        binding.editProfile.setOnClickListener({
            val intent = Intent(activity, SignUpActivity::class.java)
            intent.putExtra("MODE", 1)
            activity?.startActivity(intent)
            activity?.finish()
        })
        viewPagerAdapter = VIewPagerAdapter(requireActivity().supportFragmentManager)
        viewPagerAdapter.addFragments(MyPostsFragment(), "My Post")
        viewPagerAdapter.addFragments(MyReelsFragment(), "My Reels")
        binding.viewpager.adapter = viewPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewpager)


        return binding.root
    }

    companion object {

    }

    override fun onStart() {
        super.onStart()
        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                val user:User = it.toObject<User>()!!
                binding.name.text = user.name
                binding.bio.text = user.email
                if(!user.image.isNullOrEmpty()){
                    Picasso.get().load(user.image).into(binding.profileImage)
                }
            }
    }
}
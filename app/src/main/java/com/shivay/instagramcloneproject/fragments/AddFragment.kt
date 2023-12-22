package com.shivay.instagramcloneproject.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shivay.instagramcloneproject.databinding.FragmentAddBinding
import com.shivay.instagramcloneproject.posts.PostActivity
import com.shivay.instagramcloneproject.posts.ReelsActivity


class AddFragment : BottomSheetDialogFragment() {
    private lateinit var binding:FragmentAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater, container, false)

        binding.post.setOnClickListener {
            requireActivity().startActivity(Intent(requireContext(), PostActivity::class.java))
            requireActivity().finish()
        }

        binding.reel.setOnClickListener {
            requireActivity().startActivity(Intent(requireContext(), ReelsActivity::class.java))
            requireActivity().finish()
        }

        return binding.root
    }

    companion object {

    }
}
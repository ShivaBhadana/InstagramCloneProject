package com.shivay.instagramcloneproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.shivay.instagramcloneproject.R
import com.shivay.instagramcloneproject.adapter.searchAdapter
import com.shivay.instagramcloneproject.databinding.FragmentSearchBinding
import com.shivay.instagramcloneproject.model.User
import com.shivay.instagramcloneproject.utils.USER_NODE


class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    lateinit var adapter: searchAdapter
    var userList = ArrayList<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentSearchBinding.inflate(inflater, container, false)
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        adapter = searchAdapter(requireContext(),userList)
        binding.rv.adapter = adapter

        Firebase.firestore.collection(USER_NODE).get().addOnSuccessListener {
            var tempList = ArrayList<User>()
            userList.clear()

                for (i in it.documents) {
                    if (i.id.toString().equals(Firebase.auth.currentUser!!.uid.toString())) {

                    } else {
                        var user: User = i.toObject<User>()!!
                        tempList.add(user)
                    }

                }
                userList.addAll(tempList)
                adapter.notifyDataSetChanged()
            
        }

        binding.searchBtn.setOnClickListener{
            var text = binding.searchView.text.toString()
            Firebase.firestore.collection(USER_NODE).whereEqualTo("name", text).get().addOnSuccessListener {
                var tempList = ArrayList<User>()
                userList.clear()
                if(it.isEmpty){

                }else{
                    for (i in it.documents) {
                        if (i.id.toString().equals(Firebase.auth.currentUser!!.uid.toString())) {

                        } else {
                            var user: User = i.toObject<User>()!!
                            tempList.add(user)
                        }

                    }
                    userList.addAll(tempList)
                    adapter.notifyDataSetChanged()
                }

            }
        }
        return binding.root
    }

    companion object {

    }

}
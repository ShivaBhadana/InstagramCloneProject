package com.shivay.instagramcloneproject.posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shivay.instagramcloneproject.databinding.ActivityPostBinding

class PostActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityPostBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener({
            finish()
        })
    }
}
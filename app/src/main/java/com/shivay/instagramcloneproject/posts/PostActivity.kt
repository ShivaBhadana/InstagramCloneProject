package com.shivay.instagramcloneproject.posts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.shivay.instagramcloneproject.HomeActivity
import com.shivay.instagramcloneproject.databinding.ActivityPostBinding
import com.shivay.instagramcloneproject.model.Post
import com.shivay.instagramcloneproject.utils.POST
import com.shivay.instagramcloneproject.utils.POST_FOLDER
import com.shivay.instagramcloneproject.utils.USER_PROFILE_FOLDER
import com.shivay.instagramcloneproject.utils.uploadImage

class PostActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityPostBinding.inflate(layoutInflater)
    }
    var imageUrl:String? = null
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()){
            uri->
        uri?.let {
            uploadImage(uri, POST_FOLDER){
                url->
                if (url != null){
                    binding.selectImage.setImageURI(uri)
                    imageUrl = url
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener({
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()
        })
        binding.selectImage.setOnClickListener({
            launcher.launch("image/*")
        })
        binding.cancelButton.setOnClickListener{
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()
        }
        binding.postButton.setOnClickListener{
            val post: Post = Post(imageUrl!!, binding.caption.editText?.text.toString())
            Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document().set(post)
                    .addOnSuccessListener {
                        startActivity(Intent(this@PostActivity, HomeActivity::class.java))
                        finish()
                }
            }
        }
    }
}
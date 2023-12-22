package com.shivay.instagramcloneproject.posts

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.shivay.instagramcloneproject.HomeActivity
import com.shivay.instagramcloneproject.databinding.ActivityReelsBinding
import com.shivay.instagramcloneproject.model.Reel
import com.shivay.instagramcloneproject.utils.REEL
import com.shivay.instagramcloneproject.utils.REEL_FOLDER
import com.shivay.instagramcloneproject.utils.uploadVideo

class ReelsActivity : AppCompatActivity() {
    val binding by lazy{
        ActivityReelsBinding.inflate(layoutInflater)
    }
    private lateinit var videoUrl:String
    lateinit var progressDialog:ProgressDialog

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()){ uri->
        uri?.let {
            uploadVideo(uri, REEL_FOLDER, progressDialog){ url->
                if (url != null){

                    videoUrl = url
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        progressDialog = ProgressDialog(this)

        binding.selectReel.setOnClickListener({
            launcher.launch("video/*")
        })

        binding.cancelButton.setOnClickListener{
            startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
            finish()
        }
        binding.postButton.setOnClickListener{
            val reel: Reel = Reel(videoUrl!!, binding.caption.editText?.text.toString())
            Firebase.firestore.collection(REEL).document().set(reel).addOnSuccessListener {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ REEL).document().set(reel)
                    .addOnSuccessListener {
                        startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
                        finish()
                    }
            }
        }
    }
}
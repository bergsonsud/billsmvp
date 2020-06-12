package com.example.billsmvp.scenes.account

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.billsmvp.R
import com.example.billsmvp.scenes.login.LoginActivity
import com.example.billsmvp.util.loadImage
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_account.*

class AccountActivity : AppCompatActivity(), AccountActivityContract.View {
    lateinit var googleSignInClient: GoogleSignInClient
    val PERMISSION_REQUEST_CODE = 1001
    val PICK_IMAGE_REQUEST = 900;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        setupSignInClient()
        logout()
        loadImage()
        chooseFile()
    }

    fun loadImage() {

        val storageRef = FirebaseStorage.getInstance().reference
        val image = storageRef.child("IMG_20200106_010334.jpg")
        image.downloadUrl.addOnSuccessListener{
            imageView.loadImage(it.toString())
        }

    }


    private fun chooseFile() {
       chooseFile.setOnClickListener {
           val intent = Intent().apply {
               type = "image/*"
               action = Intent.ACTION_GET_CONTENT
           }
           startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
       }

    }


    override fun logout() {
        logout.setOnClickListener {
            googleSignInClient.signOut().addOnCompleteListener {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, LoginActivity::class.java))
            }

        }
    }

    private fun setupSignInClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient( this, gso)
    }
}
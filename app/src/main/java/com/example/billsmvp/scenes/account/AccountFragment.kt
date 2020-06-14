package com.example.billsmvp.scenes.account

import android.Manifest.permission.CAMERA
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.billsmvp.R
import com.example.billsmvp.scenes.login.LoginActivity
import com.example.billsmvp.util.loadImage
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.sembozdemir.permissionskt.askPermissions
import kotlinx.android.synthetic.main.activity_account.*

class AccountFragment : Fragment(), AccountActivityContract.View {
    lateinit var googleSignInClient: GoogleSignInClient
    val PERMISSION_REQUEST_CODE = 1001
    val PICK_IMAGE_REQUEST = 900;
    val KEY_PHOTO_REQUEST = 7777
    lateinit var selectedImage: Bitmap
    var image_uri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.activity_account,container,false)

    }

    override fun onStart() {
        super.onStart()
        setupSignInClient()
        logout()
        //loadImage()
        chooseFile()
    }

    private fun loadImage() {

        val storageRef = FirebaseStorage.getInstance().reference
        val image = storageRef.child("IMG_20200106_010334.jpg")
        image.downloadUrl.addOnSuccessListener{
            imageView.loadImage(it.toString())
        }

    }


    private fun chooseFile() {
        chooseFile.setOnClickListener {
            requestCameraPermission()
        }

    }


    override fun logout() {
        logout.setOnClickListener {
            googleSignInClient.signOut().addOnCompleteListener {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(context, LoginActivity::class.java))
            }

        }
    }

    private fun setupSignInClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context as Activity, gso)
    }

    private fun requestCameraPermission() {
        (context as? Activity)?.let {
            it.askPermissions(CAMERA) {
                onGranted {
                    openCamera()
                }
            }
        }
    }

    private fun showCamera() {
        val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, KEY_PHOTO_REQUEST)
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = context?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, KEY_PHOTO_REQUEST)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            imageView.setImageURI(image_uri)

        }
    }

    private fun didSelectImage(image: Bitmap) {
        selectedImage = image
        imageView.setImageBitmap(image)
    }

    private fun checkSelectedImage() {
        val visibility = if (selectedImage != null) View.GONE else View.VISIBLE

    }
}